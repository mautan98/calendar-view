package com.namviet.vtvtravel.view.fragment.f2mytrip.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.UserListItem

class OverlapDecoration(var listAvt:MutableList<UserListItem>) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == listAvt.size - 1)
            return
        outRect.set(-25, 0, 0, 0)
    }
}