package com.namviet.vtvtravel.adapter.f2search

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import kotlinx.android.synthetic.main.f2_item_search_category.view.*
import kotlinx.android.synthetic.main.f2_item_search_suggestion_2.view.*

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
        }else if(searchKeywordSuggestion?.get(position)?.type == "suggestion"){
            TYPE_ITEM
        }else{
            TYPE_RECENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_suggestion_2, parent, false)
            return HeaderViewHolder(v)
        }else if (viewType == TYPE_CATEGORY){
            v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_category, parent, false)
            return CategoryHolder(v)
        }else{
            v = LayoutInflater.from(parent.context).inflate(R.layout.f2_item_search_recent_2, parent, false)
            return RecentHolder(v)
        }
//        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (getItemViewType(position) == TYPE_ITEM) {
                (holder as HeaderViewHolder).bindItem(position)
            }else if (getItemViewType(position) == TYPE_CATEGORY){
                (holder as CategoryHolder).bindItem(position)
            }else{
                (holder as RecentHolder).bindItem(position)
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
            itemView.tvCategoryTitle.text = position?.let { searchKeywordSuggestion?.get(it)?.getTitle() }
        }
    }

    inner class RecentHolder : RecyclerView.ViewHolder {
        private var position: Int? = 0

        constructor(itemView: View?) : super(itemView!!) {
            itemView?.setOnClickListener {
                clickItem?.onClickItem(position?.let { it1 -> searchKeywordSuggestion?.get(it1) })
            }
        }


        fun bindItem(position: Int?) {
            this.position = position
            itemView.tvCategoryTitle.text = position?.let { searchKeywordSuggestion?.get(it)?.getTitle() }
//            setHighLightedText(itemView.tvCategoryTitle, )
        }
    }

    interface ClickItem {
        fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?)
    }


    fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val tvt = tv.text.toString()
        var ofe = tvt.indexOf(textToHighlight, 0)
        val wordToSpan: Spannable = SpannableString(tv.text)
        var ofs = 0
        while (ofs < tvt.length && ofe != -1) {
            ofe = tvt.indexOf(textToHighlight, ofs)
            if (ofe == -1) break else {
                // set color here
                wordToSpan.setSpan(ForegroundColorSpan(context!!.resources!!.getColor(R.color.md_black_1000)), ofe, ofe + textToHighlight.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE)
            }
            ofs = ofe + 1
        }
    }


    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_CATEGORY = 1
        private const val TYPE_RECENT = 2
    }

}