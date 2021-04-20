package com.namviet.vtvtravel.ultils.highlight

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import com.namviet.vtvtravel.R

class SearchHighLightText : IHighLightText {
    override fun highLight(context: Context, tv: TextView, textToHighlight: String) {
        try {
            val tvt = tv.text.toString().toLowerCase()
            var ofe = tvt.indexOf(textToHighlight.toLowerCase(), 0)
            val wordToSpan: Spannable = SpannableString(tv.text)
            var ofs = 0
            while (ofs < tvt.length && ofe != -1) {
                ofe = tvt.indexOf(textToHighlight, ofs, true)
                if (ofe == -1) break else {
                    // set color here
                    wordToSpan.setSpan(ForegroundColorSpan(context!!.resources!!.getColor(R.color.md_black_1000)), ofe, ofe + textToHighlight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    wordToSpan.setSpan(StyleSpan(Typeface.BOLD), ofe, ofe + textToHighlight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
                }
                ofs = ofe + 1
            }
        } catch (e: Exception) {
        }
    }
}