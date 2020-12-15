package com.namviet.vtvtravel.view.fragment.f2webview

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.vqmm.LeaderBoardAdapter
import com.namviet.vtvtravel.databinding.F2FragmentLeaderBoardLuckyWheelBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.f2travelvoucher.CategoryVoucherResponse
import com.namviet.vtvtravel.response.f2travelvoucher.ListVoucherResponse
import com.namviet.vtvtravel.response.f2travelvoucher.RegionVoucherResponse
import com.namviet.vtvtravel.response.f2wheel.WheelChartResponse
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import com.namviet.vtvtravel.viewmodel.f2travelvoucher.TravelVoucherViewModel
import kotlinx.android.synthetic.main.f2_fragment_leader_board_lucky_wheel.*
import java.util.*

class LeaderBoardLuckyWheelFragment : BaseFragment<F2FragmentLeaderBoardLuckyWheelBinding?>() , Observer{
    private var luckyWheelViewModel : LuckyWheelViewModel? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_leader_board_lucky_wheel
    }

    override fun initView() {
        luckyWheelViewModel = LuckyWheelViewModel();
        luckyWheelViewModel?.addObserver(this)
        luckyWheelViewModel?.getWheelChart()
    }
    override fun initData() {



    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
    override fun update(observable: Observable?, o: Any?) {
        hideLoading()
        if (observable is LuckyWheelViewModel && null != o) {
            if (o is WheelChartResponse) {
                var leaderBoardAdapter = LeaderBoardAdapter(o.data.items, mActivity);
                rclContent.adapter = leaderBoardAdapter
            }  else if (o is ErrorResponse) {

            }
        }
    }
}