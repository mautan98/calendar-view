package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F3FragmentDropDownStatusBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children
import kotlinx.android.synthetic.main.f3_fragment_drop_down_status.*

class DropDownStatusFragment : BaseFragment<F3FragmentDropDownStatusBinding?>() {
    private var listener: Listener? = null
    private var isOpen: Boolean? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_status
    }

    override fun initView() {}
    override fun initData() {
        if(isOpen == null){
            switchStatus.isEnabled = false
            switchStatus.alpha = 0.5f
        }else{
            switchStatus.alpha = 1f
            switchStatus.isChecked = isOpen!!
        }
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}

    public interface Listener{
        fun onApply(isOpen: Boolean)
    }

    public fun setData(isOpen: Boolean, listener: Listener){
        this.isOpen = isOpen
        this.listener = listener
    }
}