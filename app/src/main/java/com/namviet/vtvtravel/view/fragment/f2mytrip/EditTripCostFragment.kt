package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentAddEstimateCostBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.CostTypesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.CostResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.util.*

class EditTripCostFragment: BaseFragment<FragmentAddEstimateCostBinding>(), Observer {

    companion object {
        const val KEY_SCHEDULE_ID = "key_schedule_id"
        fun newInstance(scheduleId:String?):EditTripCostFragment {
            val args = Bundle()
            args.putString(KEY_SCHEDULE_ID,scheduleId)
            val fragment = EditTripCostFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentAddEstimateCostBinding
    private var costTypeAdapter: CostTypesAdapter?= null
    private var listTypeCost: MutableList<TypeCost> = mutableListOf()
    private var tripViewModel: MyTripsViewModel = MyTripsViewModel()
    private var scheduleId:String?= null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_estimate_cost
    }

    override fun initView() {
        scheduleId = arguments?.getString(KEY_SCHEDULE_ID)
        binding = getBinding()
        costTypeAdapter = CostTypesAdapter(requireContext())
        costTypeAdapter?.setSumTotalCostListener(object :CostTypesAdapter.SumTotalCost{
            override fun onTotalPriceChange(total: Float) {
                binding.tvTotalCost.text = total.toString()
            }
        })
        initListType()
        costTypeAdapter?.setListTypeCost(listTypeCost)
        binding.rcvTypeOfCost.adapter = costTypeAdapter
        tripViewModel.getCostList(scheduleId)
        tripViewModel.addObserver(this)

    }

    private fun initListType(){
        listTypeCost.add(TypeCost(R.drawable.ic_type_eating,getString(R.string.type_eating),false))
        listTypeCost.add(TypeCost(R.drawable.ic_type_visiting,getString(R.string.type_visiting),false))
        listTypeCost.add(TypeCost(R.drawable.ic_type_shopping,getString(R.string.type_shopping),false))
        listTypeCost.add(TypeCost(R.drawable.ic_type_eating,getString(R.string.type_transportation),false))
    }

    override fun initData() {

    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is CostResponse){
            listTypeCost.addAll(arg.data as MutableList<TypeCost>)
            listTypeCost.add(TypeCost())
            costTypeAdapter?.setListTypeCost(listTypeCost)
        }
    }
}