package com.namviet.vtvtravel.view.fragment.f2webview

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.vqmm.LeaderBoardAdapter
import com.namviet.vtvtravel.api.WSConfig
import com.namviet.vtvtravel.databinding.F3VqmmHistoriesBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.f2wheel.WheelChartResponse
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import kotlinx.android.synthetic.main.f3_vqmm_histories.*
import java.util.*

class LuckyWheelHistoriesFragment : BaseFragment<F3VqmmHistoriesBinding?>(), Observer {
    private var luckyWheelViewModel: LuckyWheelViewModel? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_vqmm_histories
    }

    override fun initView() {
        luckyWheelViewModel = LuckyWheelViewModel();
        luckyWheelViewModel?.addObserver(this)
        luckyWheelViewModel?.getVQMMHistories(WSConfig.API_VQMM_HISTORY)
    }

    override fun initData() {


    }

    override fun inject() {}
    override fun setClickListener() {
        binding?.btnBack?.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}
    override fun update(observable: Observable?, o: Any?) {
        hideLoading()
        if (observable is LuckyWheelViewModel && null != o) {
            if (o is WheelChartResponse) {
                var leaderBoardAdapter = LeaderBoardAdapter(o.data.items, mActivity);
                rclContent.adapter = leaderBoardAdapter
            } else if (o is ErrorResponse) {

            }
        }
    }
}