package com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost

import com.google.gson.annotations.SerializedName

class TypeCost {

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

    constructor(resourceImage: Int, costName: String?) {
        this.resourceImage = resourceImage
        this.costName = costName
    }

    constructor()

    @field:SerializedName("nameCost")
    var costName: String? = null
        get() = field
        set(value) {
            field = value
        }
    @field:SerializedName("prices")
     var pricePP: Float = 0f
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
    var totalPrice: Float = 0f
        get() = field
        set(value) {
            field = value
        }

}