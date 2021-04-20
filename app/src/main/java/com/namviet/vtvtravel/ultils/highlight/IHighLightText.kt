package com.namviet.vtvtravel.ultils.highlight

import android.content.Context
import android.widget.TextView

interface IHighLightText {
    fun highLight(context: Context, tv: TextView, textToHighlight: String);
}