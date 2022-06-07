package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutAddOtherCostBinding
import com.namviet.vtvtravel.databinding.LayoutItemTypeCostBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class CostTypesAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_ADD_OTHER_COST = -1
    private var listTypeCost:MutableList<TypeCost> = mutableListOf()

    fun setListTypeCost(list: MutableList<TypeCost>) {
        this.listTypeCost = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ADD_OTHER_COST){
            val binding:LayoutAddOtherCostBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_add_other_cost,parent,false)
            return AddCostViewHolder(binding)
        }
        val binding:LayoutItemTypeCostBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_item_type_cost,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val typeCost = listTypeCost.get(position)
        if (getItemViewType(position) == TYPE_ADD_OTHER_COST){
            (holder as CostTypesAdapter.AddCostViewHolder).bind()
        } else {
            (holder as CostTypesAdapter.ViewHolder).bind(typeCost)
        }
    }

    override fun getItemCount(): Int {
        return listTypeCost.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == listTypeCost.size-1){
            return TYPE_ADD_OTHER_COST
        } else {
            return super.getItemViewType(position)
        }
    }

    inner class ViewHolder(var binding: LayoutItemTypeCostBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(typeCost:TypeCost){
            binding.imvCostType.setImageResource(typeCost.resourceImage)
            binding.tvTypeName.text = typeCost.costName
            RxTextView.afterTextChangeEvents(binding.edtPrice)
                .skipInitialValue()
                .debounce(790, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    try {
                        val priceFloat = it.editable().toString().toFloat()
                        val amount = binding.tvAmount.text.toString().toFloat()
                        binding.tvTotalPrice.text = "${priceFloat * amount}"
                    } catch (e: Exception) {
                    }
                }
        }
    }

    inner class AddCostViewHolder(var binding: LayoutAddOtherCostBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.tvAddNewCostType.setOnClickListener {

            }
        }

    }
}