package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import android.widget.Toast
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.Utils
import com.namviet.vtvtravel.databinding.FragmentAddEstimateCostBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.adapter.CostTypesAdapter
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.AddNewCostBottomDialog
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.CostResponse
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.math.BigDecimal
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
    private var isEditAble:Boolean = true
    private lateinit var onBackFragment:OnBackFragmentListener

    fun setOnBackFragmentListener(onBack: OnBackFragmentListener) {
        this.onBackFragment = onBack
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_estimate_cost
    }

    override fun initView() {
        scheduleId = arguments?.getString(KEY_SCHEDULE_ID)
        binding = getBinding()
        costTypeAdapter = CostTypesAdapter(requireContext())
        costTypeAdapter?.setOnItemClickListener(object : CostTypesAdapter.OnItemCostClickListener{
            override fun onItemClick(isEditAble: Boolean) {
                if (!isEditAble){
                    showErrorEditable(childFragmentManager)
                }
            }

        })
        costTypeAdapter?.setIsEditAble(isEditAble)
        costTypeAdapter?.setSumTotalCostListener(object :CostTypesAdapter.SumTotalCost{
            override fun onTotalPriceChange(total: BigDecimal) {
                binding.tvTotalCost.text = Utils.convertPriceTrips(total)
            }
        })
        costTypeAdapter?.setAddNewCostListener(object :CostTypesAdapter.AddNewCost{
            override fun onClickAddNewCost(editable: Boolean) {
                if (!editable){
                    showErrorEditable(childFragmentManager)
                    return
                }
                val dialog = AddNewCostBottomDialog.newInstance()
                dialog.setAddDoneListener(object : AddNewCostBottomDialog.AddNewCostDoneListener{
                    override fun onAddDone(typeCost: TypeCost) {
                        listTypeCost.add(listTypeCost.size - 1,typeCost)
                        costTypeAdapter?.setListTypeCost(listTypeCost)
                    }

                })
                dialog.show(childFragmentManager,null)
            }

        })
        initListType()
        costTypeAdapter?.setListTypeCost(listTypeCost)
        binding.rcvTypeOfCost.adapter = costTypeAdapter
        tripViewModel.getCostList(scheduleId)
        tripViewModel.addObserver(this)
        initClickListener()

    }

    private fun initClickListener(){
        binding.imvBackCost.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.tvAddNewCostType.setOnClickListener {
            if (!isEditAble){
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            val dialog = AddNewCostBottomDialog.newInstance()
            dialog.setAddDoneListener(object : AddNewCostBottomDialog.AddNewCostDoneListener{
                override fun onAddDone(typeCost: TypeCost) {
                    listTypeCost.add(listTypeCost.size - 1,typeCost)
                    costTypeAdapter?.setListTypeCost(listTypeCost)
                }

            })
            dialog.show(childFragmentManager,null)
        }
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
        binding.btnUpdateCost.setOnClickListener {
            if (!isEditAble){
                showErrorEditable(childFragmentManager)
                return@setOnClickListener
            }
            val list = costTypeAdapter?.getListTypeCost()
            list?.size?.minus(1)?.let { it -> list.removeAt(it) }
            for (i in 0..list?.size?.minus(1)!!){
                if (list.get(i).scheduleCustomId == null){
                    list.get(i).scheduleCustomId = scheduleId
                }
            }
            tripViewModel.updateCost(list)
        }
    }

    override fun setObserver() {
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is CostResponse){

            val listResponse = arg.data as MutableList<TypeCost>
            for (i in 0..listResponse.size - 1){
                val typeCost = listResponse.get(i)
                when(typeCost.costName){
                    "Ăn uống" -> {
                        typeCost.resourceImage = R.drawable.ic_type_eating
                        typeCost.removeAble = false
                    }
                    "Thăm quan" -> {
                        typeCost.resourceImage = R.drawable.ic_type_visiting
                        typeCost.removeAble = false
                    }
                    "Mua sắm" -> {
                        typeCost.resourceImage = R.drawable.ic_type_shopping
                        typeCost.removeAble = false
                    }
                    "Di chuyển" -> {
                        typeCost.resourceImage = R.drawable.ic_type_eating
                        typeCost.removeAble = false
                    }
                }
            }
            val listDefault:MutableList<String?> = mutableListOf()
            for (i in 0..listTypeCost.size - 1){
                listDefault.add(listTypeCost.get(i).costName)
            }
            val listRes:MutableList<String?> = mutableListOf()
            for (i in 0..listResponse.size - 1){
                listRes.add(listResponse.get(i).costName)
            }
            if (listRes.containsAll(listDefault)){
                listTypeCost = listResponse
            } else {
                listTypeCost.addAll(listResponse)
            }
            for (i in 0..listTypeCost.size - 1){
                if (listTypeCost.get(i).resourceImage == 0){
                    listTypeCost.get(i)
                }
            }
            listTypeCost.add(TypeCost())
            costTypeAdapter?.setListTypeCost(listTypeCost)
        } else if (arg is BaseResponse) {
            if (arg.isSuccess){
                onBackFragment.onBackFragment()
                activity?.onBackPressed()
            }
        }
    }

    fun setIsEditAble(editable: Boolean) {
        isEditAble = editable
    }

    interface OnBackFragmentListener{
        fun onBackFragment()
    }
}