package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.LayoutAddOtherCostBinding
import com.namviet.vtvtravel.databinding.LayoutItemTypeCostBinding
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost.TypeCost
import io.reactivex.android.schedulers.AndroidSchedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class CostTypesAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_ADD_OTHER_COST = -1
    private var listTypeCost:MutableList<TypeCost> = mutableListOf()
    private lateinit var sumTotalCostListener:SumTotalCost
    private lateinit var addNewCostListener:AddNewCost

    fun setListTypeCost(list: MutableList<TypeCost>) {
        this.listTypeCost = list
        notifyDataSetChanged()
    }

    fun setSumTotalCostListener(sumTotalCostListener:SumTotalCost){
        this.sumTotalCostListener = sumTotalCostListener
    }

    fun setAddNewCostListener(addNewCostListener:AddNewCost){
        this.addNewCostListener = addNewCostListener
    }

    fun getListTypeCost():MutableList<TypeCost>{
        return listTypeCost
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
            (holder as CostTypesAdapter.ViewHolder).bind(typeCost,position)
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
        private var isClick = false
        fun bind(typeCost:TypeCost,position:Int){
            binding.imvCostType.setImageResource(if (typeCost.resourceImage != 0) typeCost.resourceImage else R.drawable.ic_money_no_coin)
            binding.tvTypeName.text = typeCost.costName
            binding.tvAmount.text = "${typeCost.amount}"
            binding.edtPrice.setText(typeCost.pricePP.toString())
            binding.tvTotalPrice.text = typeCost.totalPrice.toString()
            binding.imbDeleteCost.visibility =
                (if (typeCost.removeAble) View.VISIBLE else View.GONE)
            RxTextView.afterTextChangeEvents(binding.edtPrice)
                .skipInitialValue()
                .debounce(790, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    try {
                        val priceFloat = it.editable().toString().toBigDecimal()
                        val amount = binding.tvAmount.text.toString().toInt()
                        changeTotal(priceFloat,amount,typeCost)
                    } catch (e: Exception) {
                    }
                }
            binding.imvPlus.setOnClickListener {
                val numberPlus = binding.tvAmount.text.toString().toInt()
                setPlusNumber(numberPlus, binding.tvAmount, binding.imvMinus, typeCost)
            }
            binding.imvMinus.setOnClickListener {
                val numberPlus = binding.tvAmount.text.toString().toInt()
                setMinusNumber(numberPlus, binding.tvAmount, binding.imvMinus, typeCost)
            }
            binding.imbDeleteCost.setOnClickListener {
                listTypeCost.removeAt(position)
                notifyItemRemoved(position)
            }
            binding.layoutHeader.setOnClickListener {
                isClick = !isClick
                if (isClick){
                    binding.llDetailTypes.visibility = View.GONE
                    binding.imvShowHide.setImageResource(R.drawable.ic_drop_down_thick_3726)
                } else {
                    binding.llDetailTypes.visibility = View.VISIBLE
                    binding.imvShowHide.setImageResource(R.drawable.ic_drop_up_thick_3726)
                }
            }
        }

        private fun changeTotal(priceFloat: BigDecimal, amount: Int,typeCost: TypeCost) {
            val totalPrice = (priceFloat.multiply(BigDecimal.valueOf(amount.toLong())))
            binding.tvTotalPrice.text = "$totalPrice"
            typeCost.pricePP = priceFloat
            typeCost.amount = amount
            typeCost.totalPrice = totalPrice
            var totalSum = BigDecimal("0")
            for (i in 0..listTypeCost.size-2){
                totalSum+= listTypeCost.get(i).totalPrice
                sumTotalCostListener.onTotalPriceChange(totalSum)
            }
        }

        private fun setPlusNumber(
            number: Int,
            tvNumber: TextView,
            imvMinus: ImageView,
            typeCost: TypeCost
        ) {
            var numberAmount = number
            if (!imvMinus.isEnabled) {
                imvMinus.isEnabled = true
                imvMinus.setImageResource(R.drawable.ic_minus_passenger)
            }
            numberAmount += 1
            tvNumber.text = numberAmount.toString()
            typeCost.amount = numberAmount
            changeTotal(typeCost.pricePP,typeCost.amount,typeCost)
        }

        private fun setMinusNumber(
            number: Int,
            tvNumber: TextView,
            imvMinus: ImageView,
            typeCost: TypeCost
        ) {
            var numberAmount = number
            if (!imvMinus.isEnabled) {
                imvMinus.isEnabled = true
                imvMinus.setImageResource(R.drawable.ic_minus_passenger)
            }
            if (numberAmount > 0) {
                numberAmount-= 1
                if (numberAmount == 0){
                    imvMinus.isEnabled = false
                    imvMinus.setImageResource(R.drawable.ic_minus_passenger_disable_min)
                }
                tvNumber.text = numberAmount.toString()
                typeCost.amount = numberAmount
                changeTotal(typeCost.pricePP,typeCost.amount,typeCost)
            }
        }
    }

    inner class AddCostViewHolder(var binding: LayoutAddOtherCostBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.tvAddNewCostType.setOnClickListener {
               addNewCostListener.onClickAddNewCost()
            }
        }

    }

    interface SumTotalCost{
        fun onTotalPriceChange(total : BigDecimal)
    }

    interface AddNewCost{
        fun onClickAddNewCost()
    }
}