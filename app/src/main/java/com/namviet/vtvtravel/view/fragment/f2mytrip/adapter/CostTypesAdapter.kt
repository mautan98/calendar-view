package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutItemTypeCostBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost

class CostTypesAdapter(var context: Context): RecyclerView.Adapter<CostTypesAdapter.ViewHolder>() {

    private var listTypeCost:MutableList<TypeCost> = mutableListOf()

    fun setListTypeCost(list: MutableList<TypeCost>) {
        this.listTypeCost = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:LayoutItemTypeCostBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_item_type_cost,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val typeCost = listTypeCost.get(position)
        holder.bind(typeCost)
    }

    override fun getItemCount(): Int {
        return listTypeCost.size
    }

    inner class ViewHolder(var binding: LayoutItemTypeCostBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(typeCost:TypeCost){
            binding.imvCostType.setImageResource(typeCost.resourceImage)
            binding.tvTypeName.text = typeCost.costName
        }
    }
}