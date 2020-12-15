package com.namviet.vtvtravel.adapter.f2chat

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.f2chat.Content
import kotlinx.android.synthetic.main.f2_item_theme.view.*

class ChangeThemeAdapter(
        private val contents: List<Content>,
        private val onContentClick: (content: Content, position: Int) -> Unit
) : RecyclerView.Adapter<ChangeThemeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.f2_item_theme, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(contents[position])
    }

    override fun getItemCount(): Int {
        return contents.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(content: Content) {
            Glide.with(itemView).load(content.pathUri).into(itemView.imgTheme)

            itemView.imgTheme.setOnClickListener {
                onContentClick.invoke(content, adapterPosition)
            }

            if (content.isClicked) {
                itemView.vBackground.visibility = View.VISIBLE
            } else {
                itemView.vBackground.visibility = View.GONE
            }
        }
    }
}

