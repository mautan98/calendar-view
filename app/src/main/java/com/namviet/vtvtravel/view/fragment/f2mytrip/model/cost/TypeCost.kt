package com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class TypeCost {


    @field:SerializedName("id")
    var id: String? = null
        get() = field
        set(value) {
            field = value
        }

    @field:SerializedName("scheduleCustomId")
    var scheduleCustomId:String?= null
        get() = field
        set(value) {
            field = value
        }

    var resourceImage: Int = 0
        get() = field
        set(value) {
            field = value
        }

    constructor(resourceImage: Int, costName: String?,removeAble:Boolean) {
        this.resourceImage = resourceImage
        this.costName = costName
        this.removeAble = removeAble
    }

    constructor()

    @field:SerializedName("nameCost")
    var costName: String? = null
        get() = field
        set(value) {
            field = value
        }
    @field:SerializedName("prices")
     var pricePP: BigDecimal = BigDecimal.ZERO
         get() = field
         set(value) {
             field = value
         }

    @field:SerializedName("numberPeople")
    var amount: Int = 0
        get() = field
        set(value) {
            field = value
        }
    @field:SerializedName("totalCost")
    var totalPrice: BigDecimal = BigDecimal.ZERO
        get() = field
        set(value) {
            field = value
        }

    @field:SerializedName("created")
    var created: Long?= null
    get() =  field
    set(value) {
        field = value
    }

    @field:SerializedName("modified")
    var modified: Long?= null
        get() =  field
        set(value) {
            field = value
        }

    var removeAble: Boolean = true
        get() = field
        set(value) {
            field = value
        }
}