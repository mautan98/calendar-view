package com.namviet.vtvtravel.ultils.highlight

import android.content.Context
import android.widget.TextView

class HighLightController(private val iHighLightText: IHighLightText) {
    fun highLight(context: Context, tv: TextView, textToHighlight: String) {
        iHighLightText.highLight(context, tv, textToHighlight)
    }

}