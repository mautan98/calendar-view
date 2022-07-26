package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.namviet.vtvtravel.BuildConfig
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.api.WSConfig
import com.namviet.vtvtravel.databinding.FragmentDetailTripBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.listener.OnBackToFragmentListener
import com.namviet.vtvtravel.listener.OnItemRecyclerClickListener
import com.namviet.vtvtravel.model.f2booking.DataHelpCenter
import com.namviet.vtvtravel.ultils.DialogUtil.Companion.showErrorDialog
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.ultils.toGone
import com.namviet.vtvtravel.ultils.toVisible
import com.namviet.vtvtravel.view.f2.MyGiftActivity
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.PlacesInScheduleAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.UserListAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.edit.EditTripBottomDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.edit.EditTripTimeBottomDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.SchedulePlaceByDaysItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.detail.PlaceScheduleResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.DetailPlacesFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.place.model.NotifyUpdateData
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.BigDecimal
import java.util.*

class DetailTripFragment: BaseFragment<FragmentDetailTripBinding>(), Observer,
    EditTripCostFragment.OnBackFragmentListener, OnBackToFragmentListener {

    companion object {

        const val KEY_TRIP_ITEM = "trip_item_key"
        const val KEY_EDITABLE = "is_editable"

        fun newInstance(tripItem: TripItem): DetailTripFragment{
            val args = Bundle()
            args.putParcelable(KEY_TRIP_ITEM,tripItem)
            val fragment = DetailTripFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var adapter: PlacesInScheduleAdapter? = null
    private lateinit var binding: FragmentDetailTripBinding
    private var tripItem: TripItem? = null
    private var viewModel = MyTripsViewModel()
    private var onBackToTripsFragment: OnBackFragmentListener? = null
    private var isEditable: Boolean = true

    fun setOnBackToTripsFragment(listener: OnBackFragmentListener) {
        this.onBackToTripsFragment = listener
    }

    fun setIsEditable(editable: Boolean) {
        this.isEditable = editable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().theme.applyStyle(R.style.ActionBarTheme, true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_trip
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this@DetailTripFragment)){
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun initView() {
        binding = getBinding()
    }

    override fun initData() {
        binding.viewLoading.toVisible()
        tripItem = arguments?.getParcelable(KEY_TRIP_ITEM)
        tripItem?.id?.let { viewModel.getDetailplaceById(it) }
        viewModel.addObserver(this)
    }

    private fun setDataToView(){
        adapter = PlacesInScheduleAdapter(requireContext())
        binding.rcvAllSchedule.adapter = adapter
        binding.tvDetailTripName.text = tripItem?.name
        binding.tvDetailTripDesc.text = tripItem?.description
        val startDate = Utils.formatTimestampTrips(tripItem?.startAt)
        val endDate = Utils.formatTimestampTrips(tripItem?.endAt)
        binding.tvDetailTimeTrips.text = "($startDate - $endDate, ${tripItem?.numberPeople} người)"
        setTextTripCost(tripItem?.estimatedCost?.toBigDecimal())
        val userAdapter = UserListAdapter(requireContext())
        val listAvt = tripItem?.userList as MutableList<UserListItem>
        userAdapter.setListAvt(listAvt)
        binding.rcvImageUser.adapter = userAdapter

        adapter?.setOnItemClickListener(object : OnItemRecyclerClickListener{
            override fun onItemClick(position: Int) {
                val fragment = DetailPlacesFragment.newInstance(tripItem)
                fragment.setOnBackToFragmentListener(this@DetailTripFragment)
                addFragment(fragment)
            }
        })
        Glide.with(requireContext()).load(tripItem?.bannerUrl).placeholder(R.drawable.img_placeholder).error(R.drawable.img_placeholder).into(binding.imvBannerDetail)
    }

    override fun inject() {
    }

    override fun setClickListener() {
        binding.tvEditTrip.setOnClickListener {
            if (!isEditable || !tripItem?.checkRights.equals("true")) {
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            val editTripBottomDialog = EditTripBottomDialog()
            val tripItemClone = Gson().fromJson(Gson().toJson(tripItem), TripItem::class.java)
            editTripBottomDialog.setList(tripItemClone)
            editTripBottomDialog.setOnBackFragmentListener(object :
                EditTripBottomDialog.OnBackFragmentListener {
                override fun onBackFragment(apiCode: String) {
                    if (apiCode.equals("back_to_invite_friend")) {
                        addFragment(InviteFriendScheduleFragment())
                        return
                    }
                    if (apiCode.equals(WSConfig.Api.DELETE_SCHEDULE)) {
                        onBackToTripsFragment?.onBackFragment()
                        activity?.onBackPressed()
                        return
                    }
                    tripItem?.id?.let { it -> viewModel.getDetailplaceById(it) }
                }

            })
            editTripBottomDialog?.show(childFragmentManager,null)
        }
        binding.layoutAddEstimateCost.setOnClickListener {
            val fragment = EditTripCostFragment.newInstance(tripItem?.id)
            fragment.setOnBackFragmentListener(this)
            fragment.setIsEditAble(tripItem?.checkRights.equals("true"))
            addFragment(fragment)
        }
        binding.tvEditTime.setOnClickListener {
            if (!isEditable || !tripItem?.checkRights.equals("true")) {
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            val dialog = tripItem?.let { it1 -> EditTripTimeBottomDialog.newInstance(it1) }
            dialog?.setOnBackFragmentListener(object :EditTripTimeBottomDialog.OnBackFragmentListener{
                override fun onBackFragment(apiCode: String) {
                   viewModel.getDetailplaceById(tripItem?.id!!)
                }

            })
            dialog?.show(childFragmentManager,null)
        }
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.tvInvieFriends.setOnClickListener {
            if (!isEditable || !tripItem?.checkRights.equals("true")) {
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            val fragment = tripItem?.id?.let { it -> InviteFriendScheduleFragment.newInstance(it) }
            addFragment(fragment)
        }
        binding.tvViewAll.setOnClickListener {
            val fragment = DetailPlacesFragment.newInstance(tripItem)
            fragment.setOnBackToFragmentListener(this@DetailTripFragment)
            addFragment(fragment)
        }
        binding.btnSaveSchedule.setOnClickListener {
            if (!isEditable || !tripItem?.checkRights.equals("true")) {
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            onBackToTripsFragment?.onBackFragment()
            activity?.onBackPressed()
        }
        binding.btnShare.setOnClickListener {
            val uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/bg_demo.jpg")
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")
                putExtra(Intent.EXTRA_SUBJECT, tripItem?.name)
                type = "text/plain"

                // (Optional) Here we're setting the title of the content
                putExtra(Intent.EXTRA_TITLE, tripItem?.name)

                // (Optional) Here we're passing a content URI to an image to be displayed
//                data = uri
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }, tripItem?.name)
            startActivity(share)
        }

        binding.btnMoreDetailTrip.setOnClickListener {
            var tripMenuDialog = TripMenuDialog();
            tripMenuDialog.setClickListener(object : TripMenuDialog.Click{
                override fun onClickHelpCenter() {
                    val dataHelpCenter = Gson().fromJson(
                        F2Util.loadJSONFromAsset(mActivity, "helpcenter_pro"),
                        DataHelpCenter::class.java
                    )
                    MyGiftActivity.startScreen(
                        mActivity,
                        dataHelpCenter.itemMenus,
                        dataHelpCenter.name
                    )
                }

                override fun onClickGoHome() {
                    mActivity.finish()
                }

            })

            addFragment(tripMenuDialog)
        }
    }

    private fun setTextTripCost(totalCost: BigDecimal?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvEstimateCost.text = Html.fromHtml(
                Utils.convertPriceTrips(totalCost),
                Html.FROM_HTML_MODE_COMPACT
            ).toString() + "đ"
        } else {
            binding.tvEstimateCost.text =
                Html.fromHtml(Utils.convertPriceTrips(totalCost)).toString() + "đ"
        }
    }

    override fun setObserver() {
    }

    override fun update(o: Observable?, arg: Any?) {
        binding.viewLoading.toGone()
        if (arg is PlaceScheduleResponse) {
            val myTripResponse = arg
            tripItem = myTripResponse.data
            setDataToView()
            adapter?.setListPlaces(myTripResponse.data?.schedulePlaceByDays as MutableList<SchedulePlaceByDaysItem>)
        } else if (arg is ErrorResponse){
            val responseError = arg
            var des = "Đã có lỗi không xác định"
            if (!TextUtils.isEmpty(responseError.getErrorCode())) {
                des = responseError.getErrorCode()
            }
            showErrorDialog(
                getString(R.string.error_title), getString(R.string.close_title),
                des, childFragmentManager
            )
        }
    }

    override fun onBackFragment() {
        tripItem?.id?.let { viewModel.getDetailplaceById(it) }
    }

    interface OnBackFragmentListener{
        fun onBackFragment()
    }

    override fun onBack() {
        tripItem?.id?.let { viewModel.getDetailplaceById(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReloadData(notify:NotifyUpdateData){
        tripItem?.id?.let { viewModel.getDetailplaceById(it) }
    }

}