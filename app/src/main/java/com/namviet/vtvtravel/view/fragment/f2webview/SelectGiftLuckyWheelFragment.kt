package com.namviet.vtvtravel.view.fragment.f2webview

import android.util.Log
import com.google.gson.Gson
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.vqmm.AllVoucherAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSelectGiftLuckyWheelBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnChooseVoucherToRoll
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse
import com.namviet.vtvtravel.response.f2travelvoucher.RegionVoucherResponse
import com.namviet.vtvtravel.response.f2travelvoucher.SortClass
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.CategoryDialog
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.RegionDialog
import com.namviet.vtvtravel.view.fragment.f2travelvoucher.SortDialog
import com.namviet.vtvtravel.viewmodel.f2travelvoucher.TravelVoucherViewModel
import kotlinx.android.synthetic.main.f2_fragment_select_gift_lucky_wheel.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList

class SelectGiftLuckyWheelFragment : BaseFragment<F2FragmentSelectGiftLuckyWheelBinding?>(), Observer {
    private var voucherSelected: ArrayList<ListVoucherResponse.Data.Voucher> = ArrayList();

    private var sortClass: SortClass? = null
    private var travelVoucherAdapter: AllVoucherAdapter? = null
    private var vouchers: ArrayList<ListVoucherResponse.Data.Voucher> = ArrayList()
    private var regionId = ""
    private var categoryId = ""
    private var sortId = "0"
    private var memberRankId = ""
    private var page = 0
    private var isFromRegVip = false
    var viewModel: TravelVoucherViewModel? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_select_gift_lucky_wheel
    }

    override fun initView() {
        viewModel = TravelVoucherViewModel()
        viewModel!!.addObserver(this)
    }

    override fun initData() {
        setDistance()
        getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip)
        viewModel?.getCategoryVoucher()
        viewModel?.getRegionVoucher()


        travelVoucherAdapter = AllVoucherAdapter(vouchers, mActivity, object : AllVoucherAdapter.ClickItem {
            override fun onClickItem(voucher: ListVoucherResponse.Data.Voucher?) {
                var detailVoucherDialog =  DetailVoucherDialog(voucher)
                detailVoucherDialog.show(mActivity.supportFragmentManager, null)
            }

            override fun onMaxSelected() {
                showDialog(LuckyWheelDialog.Type.SAD_TYPE, "Bạn chỉ được chọn tối đa 3 Voucher")
            }
        }, true)
        rclContent.adapter = travelVoucherAdapter


    }

    private fun showDialog(type: Int, title: String) {
        var luckyWheelDialog = LuckyWheelDialog.newInstance(LuckyWheelDialog.ClickButton {

        }, type, title)
        luckyWheelDialog.show(mActivity.supportFragmentManager, null)
    }

    override fun inject() {}
    override fun setClickListener() {
        btnSelectedGift.setOnClickListener {
            voucherSelected.clear()
            for (i in vouchers.indices){
                if(vouchers[i].isSelectedLuckyWheel){
                    voucherSelected.add(vouchers[i])
                }
            }

            if(voucherSelected.size >0){
                var selectedvoucherdialog = SelectedVoucherDialog(SelectedVoucherDialog.RollNow {
                    try {
                        var strings: ArrayList<String>  = ArrayList()
                        for (j in voucherSelected.indices){
                            strings.add(voucherSelected[j].id)
                        }

                        EventBus.getDefault().post(OnChooseVoucherToRoll(strings))
                        mActivity.onBackPressed()
                    } catch (e: Exception) {
                    }
                }, voucherSelected);
                selectedvoucherdialog.show(mActivity.supportFragmentManager, null)
            }else{
                showDialog(LuckyWheelDialog.Type.SAD_TYPE, "Bạn chưa chọn Voucher nào")
            }


        }

        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }


        btnRegionFilter.setOnClickListener {
            try {
                val regionDialog = RegionDialog(regionVoucherResponse, RegionDialog.DoneSort { category ->
                    binding!!.tvRegionFilterName.text = category.name
                    regionId = category.id
                    clearDataRCL()

                    getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip)
                }, "Khu vực", RegionDialog.Type.LUCKY_WHEEL_TYPE)
                regionDialog.show(mActivity.supportFragmentManager, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnCategoryFilter.setOnClickListener {
            try {
                val categoryDialog = CategoryDialog(categoryVoucherResponse, CategoryDialog.DoneSort { category ->
                    tvCategoryFilterName.text = category.name
                    categoryId = category.id
                    clearDataRCL()
                    Log.e("Travelllllllllllllllll", "2")
                    getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip)
                }, "Danh mục", CategoryDialog.Type.LUCKY_WHEEL_TYPE)
                categoryDialog.show(mActivity.supportFragmentManager, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btnSort.setOnClickListener {
            val sortDialog = SortDialog(sortClass, SortDialog.DoneSort { sort ->
                try {
                    tvSortName.text = sort.label
                    sortId = sort.value
                    clearDataRCL()
                    Log.e("Travelllllllllllllllll", "3")
                    getVoucher("VTVTRAVEL", sortId, regionId, memberRankId, categoryId, page, isFromRegVip)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, "Sắp xếp", SortDialog.Type.LUCKY_WHEEL_TYPE)
            sortDialog.show(mActivity.supportFragmentManager, null)
        }

    }

    private fun getVoucher(service: String, sort: String, regionId: String, memberRankId: String, categoryId: String, page: Int, isFromRegVip: Boolean) {
        viewModel?.getOwnedVoucherStoreNonToken(service, sort, regionId, memberRankId, categoryId, page)
        try {
            TrackingAnalytic.postEvent(TrackingAnalytic.FILTER_PROMOTION, TrackingAnalytic.getDefault("Travel Voucher", "Danh sách voucher").setScreen_class(this.javaClass.name))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clearDataRCL() {
        page = 0
        vouchers.clear()
        travelVoucherAdapter?.resetCountSelected()
        travelVoucherAdapter?.notifyDataSetChanged()
    }

    fun setDistance() {
        sortClass = Gson().fromJson<SortClass>(loadJSONFromAsset(), SortClass::class.java)
    }


    override fun setObserver() {}

    private var categoryVoucherResponse: CategoryVoucherResponse? = null
    private var regionVoucherResponse: RegionVoucherResponse? = null
    override fun update(observable: Observable?, o: Any?) {
        hideLoading()
        if (observable is TravelVoucherViewModel && null != o) {
            if (o is CategoryVoucherResponse) {
                categoryVoucherResponse = o
                var category = CategoryVoucherResponse().Category()
                category.isChecked = true
                category.name = "Tất cả"
                category.id = ""
                categoryVoucherResponse?.data?.add(0, category)
            } else if (o is ListVoucherResponse) {
                var listVoucherResponse = o
                if (listVoucherResponse.data.vouchers != null && listVoucherResponse.data.vouchers.size > 0) {
                    page += 1
                    vouchers.addAll(listVoucherResponse.data.vouchers)
                    travelVoucherAdapter?.notifyDataSetChanged()
                }
//                try {
//                    if (vouchers.size > 0) {
//                        binding.imgOutDateVoucherHaveButton.setVisibility(View.GONE)
//                        binding.imgOutDateVoucher.setVisibility(View.GONE)
//                    } else {
//                        if (isStore) {
//                            binding.imgOutDateVoucherHaveButton.setVisibility(View.VISIBLE)
//                            binding.imgOutDateVoucher.setVisibility(View.GONE)
//                        } else {
//                            binding.imgOutDateVoucherHaveButton.setVisibility(View.GONE)
//                            binding.imgOutDateVoucher.setVisibility(View.VISIBLE)
//                        }
//                    }
//                } catch (e: Exception) {
//                    binding.imgOutDateVoucherHaveButton.setVisibility(View.GONE)
//                    binding.imgOutDateVoucher.setVisibility(View.GONE)
//                }
            } else if (o is RegionVoucherResponse) {
                regionVoucherResponse = o
                val category = RegionVoucherResponse().Category()
                category.isSelected = true
                category.name = "Tất cả"
                category.id = ""
                regionVoucherResponse?.data?.add(0, category)
            } else if (o is ErrorResponse) {

            }
        }
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            var inputStream = mActivity.assets.open("sortvoucher.json")
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}