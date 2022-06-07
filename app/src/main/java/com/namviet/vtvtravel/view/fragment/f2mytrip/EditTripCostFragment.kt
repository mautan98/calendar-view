package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentAddEstimateCostBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.CostTypesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost

class EditTripCostFragment: BaseFragment<FragmentAddEstimateCostBinding>() {

    companion object {

        fun newInstance():EditTripCostFragment {
            val args = Bundle()
            val fragment = EditTripCostFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentAddEstimateCostBinding
    private var costTypeAdapter: CostTypesAdapter?= null
    private var listTypeCost: MutableList<TypeCost> = mutableListOf()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_estimate_cost
    }

    override fun initView() {
        binding = getBinding()
        costTypeAdapter = CostTypesAdapter(requireContext())
        initListType()
        costTypeAdapter?.setListTypeCost(listTypeCost)
        binding.rcvTypeOfCost.adapter = costTypeAdapter

    }

    private fun initListType(){
        listTypeCost.add(TypeCost(R.drawable.ic_type_eating,getString(R.string.type_eating)))
        listTypeCost.add(TypeCost(R.drawable.ic_type_visiting,getString(R.string.type_visiting)))
        listTypeCost.add(TypeCost(R.drawable.ic_type_shopping,getString(R.string.type_shopping)))
        listTypeCost.add(TypeCost(R.drawable.ic_type_eating,getString(R.string.type_transportation)))
        listTypeCost.add(TypeCost())
    }

    override fun initData() {

    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }
}