package com.namviet.vtvtravel.view.fragment.f2video

import androidx.fragment.app.FragmentTransaction
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentDropDownLocationInVideoBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.travelnews.Location
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location_in_video.*

class DropDownLocationInVideoFragment : BaseFragment<F3FragmentDropDownLocationInVideoBinding?>() {
    private var callback : Callback? = null
    private var city : Location? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_location_in_video
    }

    override fun initView() {
        if(!content?.cityId.isNullOrBlank()){
            tvCity.text = content?.cityName
        }
    }
    override fun initData() {
    }
    override fun inject() {}
    override fun setClickListener() {
        btnChooseCity.setOnClickListener {
            callback?.onClickChooseLocation()
        }

        btnApply.setOnClickListener {
            var content = Content()
            content.cityName = this.content?.cityName
            content.cityId = this.content?.cityId
            callback?.onApply(content)
        }

        btnClearFilter.setOnClickListener {
            var content = Content()
            callback?.onApply(content)
        }
    }
    override fun setObserver() {}

    public fun setData( callback: Callback?) {
        this.callback = callback
    }

    public interface Callback{
        fun onClickChooseLocation()
        fun onApply(content : Content)
    }

    private var content  :Content? = null
    public fun setData (content : Content){
        this.content = content
        try {
            tvCity.text = content.cityName
        } catch (e: Exception) {
        }
    }

}