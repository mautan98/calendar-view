package com.namviet.vtvtravel.view.fragment.share

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2share.ContactShareAdapter
import com.namviet.vtvtravel.adapter.f2share.ContactShareSelectedAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentContactShareBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2.Contact
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.ultils.ValidateUtils
import com.namviet.vtvtravel.view.MainActivity
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.viewmodel.f2share.ShareViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_chat.view.*
import kotlinx.android.synthetic.main.f2_fragment_contact_share.*
import java.text.Collator
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ContactShareFragment (private var title : String, private var link : String, private var image : String, private var contentType: String) : BaseFragment<F2FragmentContactShareBinding?>(), Observer {

    private var contactShareAdapter: ContactShareAdapter? = null
    private var contactShareSelectedAdapter: ContactShareSelectedAdapter? = null
    private var listContact: ArrayList<Contact> = ArrayList()
    private var listAllContact: ArrayList<Contact> = ArrayList()
    private var listContactSelected: ArrayList<Contact> = ArrayList()
    private var hashMapSelected = HashMap<Int, Boolean>()
    private var shareViewModel : ShareViewModel? = null

    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_contact_share
    }

    override fun initView() {}
    override fun initData() {
        shareViewModel = ShareViewModel();
        shareViewModel?.addObserver(this)
        setDataFromIntent()
        setDataRclContactSelected()
        rclContactSelected.adapter = contactShareSelectedAdapter
        checkPermission()
        handleSearch()
    }

    private fun setDataRclContactSelected(){
        contactShareSelectedAdapter = ContactShareSelectedAdapter(mActivity, listContactSelected, ContactShareSelectedAdapter.ClickItem { a ->

            val isSelected = hashMapSelected[a.contactClientId]
            if (isSelected != null) {
                if (isSelected) {
                    hashMapSelected[a.contactClientId] = false
                } else {

                }
            } else {
            }

            contactShareAdapter?.notifyDataSetChanged()

        }, ContactShareSelectedAdapter.ShowHideText {isShow, count ->
            if (isShow) {
                tvNotYetSelected.visibility = View.VISIBLE
                layoutContentShare.visibility = View.GONE
            } else {
                tvNotYetSelected.visibility = View.GONE
                layoutContentShare.visibility = View.VISIBLE
            }

            if(count == 0){
                tvTitle.text = "Đã chọn"
            }else{
                tvTitle.text = "Đã chọn ($count)"
            }

        })
    }

    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnSend.setOnClickListener {
            val account = MyApplication.getInstance().account
            if (null != account && account.isLogin) {
                showLoading()
                shareViewModel?.share(contentType, link, getListContactToShare())
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false)
            }

        }
    }

    private fun getListContactToShare(): ArrayList<String?>{
        var result = ArrayList<String?>();
        for (i in listContactSelected.indices){
            result.add(listContactSelected[i].phones[0])
        }
        return result
    }

    private fun setDataFromIntent(){
        try {
            tvShareName.text = title
            tvShareLink.text = link
            F2Util.loadImageToImageView(mActivity, image, imgAvatar)
        } catch (e: Exception) {
        }
    }

    override fun setObserver() {}


    @SuppressLint("StaticFieldLeak")
    fun getContacts(ctx: Context) {
        showLoading()
        val list = ArrayList<Contact>()
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                try {
                    edtSearch.isEnabled = true
                    hideLoading()
                    contactShareAdapter = ContactShareAdapter(mActivity, listContact, ContactShareAdapter.ClickItem { contact, isAdd ->
                        if (isAdd) {
                            listContactSelected.add(contact)
                        } else {
                            for (i in listContactSelected.indices) {
                                if (listContactSelected[i].contactClientId == contact.contactClientId) {
                                    listContactSelected.removeAt(i)
                                    break
                                }
                            }
                        }

                        contactShareSelectedAdapter?.notifyDataSetChanged()
                    }, hashMapSelected)
                    rclContact.adapter = contactShareAdapter
                } catch (e: Exception) {
                }
            }

            override fun doInBackground(vararg params: Void?): Void? {
                try {
                    val contentResolver = ctx.contentResolver
                    val cursor: Cursor?
                    cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

                    if (cursor!!.count > 0) {
                        while (cursor.moveToNext()) {
                            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                            if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                val cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)
                                val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.contentResolver,
                                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong()))
                                val person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
                                val pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
                                var photo: Bitmap? = null
                                if (inputStream != null) {
                                    photo = BitmapFactory.decodeStream(inputStream)
                                }
                                while (cursorInfo!!.moveToNext()) {
                                    val contact = Contact()
                                    //                        contact.setId(Integer.parseInt(id));
                                    contact.id = 0
                                    contact.contactClientId = id.toInt()
                                    contact.contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                                    val phone = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ", "")
                                    val phones: MutableList<String> = java.util.ArrayList()
                                    phones.add(ValidateUtils.isPhoneValidateV2(phone, 84))
                                    contact.phones = phones
                                    contact.phoneToShow = phone
                                    list.add(contact)
                                }
                                cursorInfo.close()
                            }
                        }
                        cursor.close()
                    }
                    listAllContact.clear()
                    listAllContact.addAll(list)
                    listContact.addAll(initHeaderContact(listAllContact))


                } catch (e: Exception) {
                    Log.e("", "")
                }
                return null
            }


        }.execute()

    }

    private fun handleSearch() {
        RxTextView.afterTextChangeEvents(edtSearch)
                .skipInitialValue()
                .debounce(450, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { search(edtSearch.text.toString()) }
    }


    fun search(s: String?) {
        if (s == null || s.isEmpty()) {
            resetListContact()
        } else {
            var listContactSearched = ArrayList<Contact>()
            for (i in listContact.indices) {
                val contact: Contact = listContact.get(i)
                if (F2Util.removeAccent(contact.contactName.toLowerCase()).contains(F2Util.removeAccent(s.toLowerCase()))) {
//                    listContact[i].isSearched = false
                    listContactSearched.add(contact)
                } else if (contact.phones != null && contact.phones.size > 0) {
                    val phone = contact.phones[0]
                    if (phone.contains(s)) {
//                        listContact[i].isSearched = false
                        listContactSearched.add(contact)
                    } else if (phone.replaceFirst("84".toRegex(), "0").contains(s)) {
//                        listContact[i].isSearched = false
                        listContactSearched.add(contact)
                    } else if (phone.replaceFirst("84".toRegex(), "+84").contains(s)) {
//                        listContact[i].isSearched = false
                        listContactSearched.add(contact)
                    }
                }
            }

            listContact.clear()
            listContact.addAll(initHeaderContact(listContactSearched))
        }

        contactShareAdapter?.notifyDataSetChanged()
    }

    private fun resetListContact() {
        listContact.clear()
        listContact.addAll(initHeaderContact(listAllContact))
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( arrayOf(Manifest.permission.READ_CONTACTS), MainActivity.MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            getContacts(mActivity)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            MainActivity.MY_PERMISSIONS_REQUEST_READ_CONTACTS -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts(mActivity)
            } else {
                showToast("Bạn cần cấp quyền truy cập danh bạ để ứng dụng có thể lấy danh sách liên hệ")
            }
        }
    }


    private fun initHeaderContact(contacts: ArrayList<Contact>): ArrayList<Contact> {
        val locale = Locale("vi_VN")
        val collator = Collator.getInstance(locale)
        contacts.sortWith(Comparator { one, other -> collator.compare(one.contactName, other.contactName) })
        var contactList: ArrayList<Contact> = ArrayList()
        var poolCharacter = ""
        try {
            for (contact in contacts) {
                val currentCharacter = contact.contactName[0].toString().toUpperCase()
                if (poolCharacter != currentCharacter) {
                    poolCharacter = currentCharacter
                    val headerContact = Contact()
                    headerContact.contactName = poolCharacter
                    headerContact.isHeader = true
                    contactList.add(headerContact)
                }
                contactList.add(contact)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return contactList
    }

    override fun update(observable: Observable?, o: Any?) {
        hideLoading()
        if (observable is ShareViewModel && null != o) {
            when (o) {
                is BaseResponse -> {
                   showToast("Chia sẻ thành công!")
                }
                is ErrorResponse -> {
                    val responseError = o
                    try {
                        showToast("Chia sẻ không thành công, vui lòng kiểm tra số điện thoại và thử lại")
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }
}