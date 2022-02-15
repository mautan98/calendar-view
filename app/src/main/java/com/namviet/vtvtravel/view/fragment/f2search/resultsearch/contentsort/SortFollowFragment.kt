package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SortParamAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSortFollowBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children
import kotlinx.android.synthetic.main.f3_fragment_sort_follow.*


class SortFollowFragment : BaseFragment<F3FragmentSortFollowBinding?>() {
    private var listChild : ArrayList<Children>? = null
    private var sortParamAdapter: SortParamAdapter? = null
    private var listener: Listener? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_sort_follow
    }

    override fun initView() {}
    override fun initData() {
        sortParamAdapter = SortParamAdapter(mActivity, listChild, null);
        binding!!.rclSortParam.adapter = sortParamAdapter
    }
    override fun inject() {}
    override fun setClickListener() {
        btnApply.setOnClickListener {
            listener?.onApply(listChild)
        }

        btnClearFilter.setOnClickListener {
            for (i in 0 until listChild!!.size){
                listChild!![i].isSelected = false
            }
            listener?.onApply(listChild)
        }
    }
    override fun setObserver() {}

    public fun setData(listChild : ArrayList<Children>?, listener: Listener){
        val listAsJson: String = Gson().toJson(listChild)
        val newList: ArrayList<Children> = Gson().fromJson<ArrayList<Children>>(
            listAsJson,
            object : TypeToken<ArrayList<Children?>?>() {}.type
        )
        this.listChild = newList
        this.listener = listener;
    }

    public interface Listener{
        fun onApply(listChild : ArrayList<Children>?)
    }
}