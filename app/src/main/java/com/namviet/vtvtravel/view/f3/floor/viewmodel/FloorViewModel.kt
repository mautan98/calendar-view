package com.namviet.vtvtravel.view.f3.floor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.namviet.vtvtravel.view.f3.commingsoon.view.ComingSoonFragment

class FloorViewModel : ViewModel() {
    var title = ObservableField("")

    fun setTextForTextView(type : String?){
        title.set(type)
    }
}