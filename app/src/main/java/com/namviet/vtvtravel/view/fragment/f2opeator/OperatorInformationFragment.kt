package com.namviet.vtvtravel.view.fragment.f2opeator

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2operator.OperatorInformationAdapter
import com.namviet.vtvtravel.databinding.F2FragmentOperatorInformationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2operator.OperatorInformation
import com.namviet.vtvtravel.response.f2operator.OperatorInformationResponse
import com.namviet.vtvtravel.ultils.DateUtltils
import com.namviet.vtvtravel.viewmodel.f2operator.OperatorViewModel
import kotlinx.android.synthetic.main.f2_fragment_operator_information.*
import java.util.*
import kotlin.collections.ArrayList

class OperatorInformationFragment : BaseFragment<F2FragmentOperatorInformationBinding?>() , Observer {
    private var listOperatorInformation = ArrayList<OperatorInformation>()
    private var operatorViewModel : OperatorViewModel? = null
    private var operatorInformationAdapter : OperatorInformationAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_operator_information
    }

    override fun initView() {}
    override fun initData() {
        operatorViewModel = OperatorViewModel();
        operatorViewModel!!.addObserver(this)
        operatorViewModel!!.getOperatorInformation()
    }
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener{
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}
    override fun update(observable: Observable?, o: Any?) {
        if (observable is OperatorViewModel && null != o) {
            when (o) {
                is OperatorInformationResponse -> {
                    try {
                        val response = o
                        listOperatorInformation.add(OperatorInformation("Số Tổng đài Du lịch Việt Nam", response.data.virtualMobile, false))
                        listOperatorInformation.add(OperatorInformation("Trạng thái", if(response.data.status == "1") "Đã kích hoạt" else "Chưa kích hoạt" , response.data.status == "1"))
                        listOperatorInformation.add(OperatorInformation("Người được cấp", response.data.fullname, false))
                        listOperatorInformation.add(OperatorInformation("Email", "", false))
                        listOperatorInformation.add(OperatorInformation("Giới tính", if(response.data.gender == "1") "Nam" else "Nữ", false))
                        listOperatorInformation.add(OperatorInformation("Ngày sinh", "", false))
                        listOperatorInformation.add(OperatorInformation("Số điện thoại", response.data.mobile, false))
                        listOperatorInformation.add(OperatorInformation("Chức vụ", response.data.userPosition, false))
                        listOperatorInformation.add(OperatorInformation("Tỉnh/Thành phố", response.data.userWorkProvince, false))
                        listOperatorInformation.add(OperatorInformation("Ngày cấp số ảo", DateUtltils.timeToString5(response.data.created.toLong()), false))

                        operatorInformationAdapter = OperatorInformationAdapter(listOperatorInformation, mActivity)
                        rclContent.adapter = operatorInformationAdapter
                    } catch (e: Exception) {
                    }
                }

                is ErrorResponse -> {
                    try {
                        val responseError = o as ErrorResponse

                    } catch (e: Exception) {
                    }
                }
            }
        }
    }
}