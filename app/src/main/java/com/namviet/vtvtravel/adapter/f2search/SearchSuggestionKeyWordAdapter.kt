package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import kotlinx.android.synthetic.main.f2_item_search_recent.view.*

class SearchSuggestionKeyWordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context? = null
    public var clickItem: ClickItem? = null
    private var searchKeywordSuggestion: List<SearchSuggestionResponse.Data.Item>? = null

    constructor(searchKeywordSuggestion: List<SearchSuggestionResponse.Data.Item>?, context: Context, clickItem: ClickItem) {
        this.context = context
        this.clickItem = clickItem
        this.searchKeywordSuggestion = searchKeywordSuggestion

        
    }

    fun setData(comments: List<SearchSuggestionResponse.Data.Item>) {
        this.searchKeywordSuggestion = comments
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if(searchKeywordSuggestion?.get(position)?.type == "category"){
            TYPE_CATEGORY
        }else{
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_suggestion_2, parent, false)
            return HeaderViewHolder(v)
        }else{
            v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_category, parent, false)
            return CategoryHolder(v)
        }
//        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                (holder as HeaderViewHolder).bindItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return try {
            searchKeywordSuggestion!!.size
        } catch (e: Exception) {
            0
        }
    }

    inner class HeaderViewHolder : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {
            itemView?.setOnClickListener {
                clickItem?.onClickItem(position?.let { it1 -> searchKeywordSuggestion?.get(it1) })
            }
        }


        fun bindItem(position: Int?) {
            this.position = position
            itemView.tvTitle.text = position?.let { searchKeywordSuggestion?.get(it)?.getTitle() }
        }
    }

    inner class CategoryHolder : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {
            itemView?.setOnClickListener {
                clickItem?.onClickItem(position?.let { it1 -> searchKeywordSuggestion?.get(it1) })
            }
        }


        fun bindItem(position: Int?) {
            this.position = position
            itemView.tvTitle.text = position?.let { searchKeywordSuggestion?.get(it)?.getTitle() }
        }
    }

    interface ClickItem {
        fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?)
    }


    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_CATEGORY = 1
    }

}