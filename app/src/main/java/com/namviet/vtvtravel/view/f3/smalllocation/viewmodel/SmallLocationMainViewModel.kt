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
    var backgroundEditTextSearch = ObservableField(R.color.f6f6f6)
    var tintBack = ObservableField(R.color.color4b4b4b)
    var stateLayoutSearch1 = ObservableField(View.GONE)
    var stateLayoutSearch2 = ObservableField(View.GONE)
    var stateBtnBack1 = ObservableField(View.GONE)


    fun setStateFirst(){
        stateCancelButton.set(View.GONE)
        backgroundToolbar.set(R.color.colorPrimary)
        stateSearchSuggestion.set(View.GONE)
        backgroundEditTextSearch.set(R.color.white)
        tintBack.set(R.color.white)
        stateLayoutSearch1.set(View.VISIBLE)
        stateLayoutSearch2.set(View.GONE)
        stateBtnBack1.set(View.VISIBLE)
    }

    fun setStateSecond(){
        stateCancelButton.set(View.VISIBLE)
        backgroundToolbar.set(R.color.white)
        stateSearchSuggestion.set(View.VISIBLE)
        backgroundEditTextSearch.set(R.color.f6f6f6)
        tintBack.set(R.color.color4b4b4b)
        stateLayoutSearch1.set(View.GONE)
        stateLayoutSearch2.set(View.VISIBLE)
        stateBtnBack1.set(View.GONE)
    }



}