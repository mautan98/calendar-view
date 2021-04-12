package com.namviet.vtvtravel.view.f3.commingsoon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.namviet.vtvtravel.view.f3.commingsoon.view.ComingSoonFragment

class ComingSoonViewModel : ViewModel() {
    var title = ObservableField("")

    fun setTextForTextView(type : String?){
        title.set(type)
    }
}