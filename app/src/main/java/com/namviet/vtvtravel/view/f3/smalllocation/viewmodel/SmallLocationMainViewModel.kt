package com.namviet.vtvtravel.view.f3.smalllocation.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.view.f3.commingsoon.view.ComingSoonFragment
import javax.inject.Inject

class SmallLocationMainViewModel @Inject constructor() : ViewModel() {
    var stateCancelButton = ObservableField(View.GONE)
    var backgroundToolbar = ObservableField(R.color.colorPrimary)
    var stateSearchSuggestion = ObservableField(View.GONE)


    fun setStateFirst(){
        stateCancelButton.set(View.GONE)
        backgroundToolbar.set(R.color.colorPrimary)
        stateSearchSuggestion.set(View.GONE)
    }

    fun setStateSecond(){
        stateCancelButton.set(View.VISIBLE)
        backgroundToolbar.set(R.color.white)
        stateSearchSuggestion.set(View.VISIBLE)
    }



}