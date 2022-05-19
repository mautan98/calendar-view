package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.CategoryDropdownAdapter
import com.namviet.vtvtravel.databinding.F3FragmentDropDownCategoryBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children
import kotlinx.android.synthetic.main.f3_fragment_drop_down_category.*

class DropDownCategoryFragment : BaseFragment<F3FragmentDropDownCategoryBinding?>() {
    private var listChild : ArrayList<Children>? = null
    private var categoryDropdownAdapter: CategoryDropdownAdapter? = null
    private var listener: Listener? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_category
    }

    override fun initView() {}
    override fun initData() {
        categoryDropdownAdapter = CategoryDropdownAdapter(mActivity, listChild, null);
        binding!!.rclSortParam.adapter = categoryDropdownAdapter
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
        this.listener = listener
    }

    public interface Listener{
        fun onApply(listChild : ArrayList<Children>?)
    }

}