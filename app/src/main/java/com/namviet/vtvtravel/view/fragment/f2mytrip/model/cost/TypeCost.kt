package com.namviet.vtvtravel.view.fragment.f2mytrip.model.cost

class TypeCost {
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

    var costName: String? = null
        get() = field
        set(value) {
            field = value
        }
     var pricePP: Float = 0f
         get() = field
         set(value) {
             field = value
         }
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