package com.namviet.vtvtravel.view.fragment.f2video

import androidx.fragment.app.FragmentTransaction
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentDropDownLocationInVideoBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Location
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.*

class DropDownLocationInVideoFragment : BaseFragment<F3FragmentDropDownLocationInVideoBinding?>() {
    private var callback : Callback? = null
    private var city : Location? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_location_in_video
    }

    override fun initView() {}
    override fun initData() {
    }
    override fun inject() {}
    override fun setClickListener() {
        btnChooseCity.setOnClickListener {
            callback?.onClickChooseLocation()
        }
    }
    override fun setObserver() {}

    public fun setData( callback: Callback?) {
        this.callback = callback
    }

    public interface Callback{
        fun onClickChooseLocation()
        fun onApply()
    }

}