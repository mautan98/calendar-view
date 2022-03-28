package com.namviet.vtvtravel.view.f3.search.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.baseapp.utils.KeyboardUtils
import com.github.nkzawa.socketio.client.On
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F3FragmentSearchSuggestionForSpecificContentBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.ultils.PreferenceUtil
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.MainActivity
import com.namviet.vtvtravel.view.f3.search.viewmodel.SearchSuggestionViewModel
import com.namviet.vtvtravel.view.fragment.f2travelnote.ResultSearchNewsActivity
import com.namviet.vtvtravel.view.fragment.f2travelnote.ResultSearchNewsFragment
import com.namviet.vtvtravel.view.fragment.f2video.ResultSearchVideoActivity
import com.namviet.vtvtravel.view.fragment.f2video.ResultSearchVideoFragment
import com.namviet.vtvtravel.view.fragment.imagepart.ResultSearchImagesActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.sentry.event.EventBuilder
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion_for_specific_content.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Handler
import javax.inject.Inject
import kotlin.collections.ArrayList

class SearchSuggestionForSpecificContentFragment(
    private var keyword: String? = null,
    private var contentType: String? = null,
    private var isFromResultPage: Boolean = false
) : BaseFragment<F3FragmentSearchSuggestionForSpecificContentBinding?>(), Observer {

    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null

    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()

    private var searchSuggestionViewModel: SearchSuggestionViewModel? = null

    private var mContext : Context ? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_suggestion_for_specific_content
    }

    override fun initView() {
        if(isFromResultPage){
            viewFullScreen.visibility = View.GONE
        }else{
            viewFullScreen.visibility = View.VISIBLE
        }
    }
    override fun initData() {
        searchSuggestionViewModel = SearchSuggestionViewModel()
        searchSuggestionViewModel?.addObserver(this)

        searchSuggestionKeyWordAdapter = SearchSuggestionKeyWordAdapter(
            searchSuggestions,
            mContext!!,
            object : SearchSuggestionKeyWordAdapter.ClickItem {
                override fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?) {
                    try {
                        when (contentType){
                            SearchSuggestionForSpecificContentActivity.Type.VIDEO -> {
                                if (!isFromResultPage) {
                                    ResultSearchVideoActivity.openScreen(mContext, searchKeywordSuggestion?.title, null, searchKeywordSuggestion)
                                    fragmentManager?.popBackStack()
                                } else {
                                    EventBus.getDefault().post(Done(searchKeywordSuggestion?.title, contentType, searchKeywordSuggestion))
                                    fragmentManager?.popBackStack()
                                }
                            }

                            SearchSuggestionForSpecificContentActivity.Type.NEWS -> {
                                if (!isFromResultPage) {
                                    ResultSearchNewsActivity.openScreen(mContext, searchKeywordSuggestion?.title, null, searchKeywordSuggestion)
                                    fragmentManager?.popBackStack()
                                } else {
                                    EventBus.getDefault().post(Done(searchKeywordSuggestion?.title, contentType, searchKeywordSuggestion))
                                    fragmentManager?.popBackStack()
                                }
                            }


                            SearchSuggestionForSpecificContentActivity.Type.IMAGE -> {
                                if (!isFromResultPage) {
                                    ResultSearchImagesActivity.openScreen(mContext, searchKeywordSuggestion?.title, null, searchKeywordSuggestion)
                                    fragmentManager?.popBackStack()
                                } else {
                                    EventBus.getDefault().post(Done(searchKeywordSuggestion?.title, contentType, searchKeywordSuggestion))
                                    fragmentManager?.popBackStack()
                                }
                            }
                        }

                    } catch (e: Exception) {
                    }
                }

            })
        rclSearchSuggestion.adapter = searchSuggestionKeyWordAdapter


        checkKeyword()
        handleSearch()

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                if (edtSearch.text.isNotEmpty()) {
//                    KeyboardUtils.hideKeyboard(mContext, edtSearch)
                    clickLayoutKeyword()
                }
                true
            } else {
                false
            }
        }

        edtSearch.requestFocus()
        android.os.Handler().postDelayed(Runnable {
            KeyboardUtils.showKeyboard(mContext, edtSearch)
        }, 500)

    }

    private fun search() {

    }

    private fun focusSearch() {
        edtSearch.requestFocus()
        KeyboardUtils.showKeyboard(mContext, edtSearch)
    }

    private fun checkKeyword() {
        if (keyword.isNullOrEmpty()) {
            layoutSearchSuggestion.visibility = View.GONE
        } else {
            layoutSearchSuggestion.visibility = View.VISIBLE
            edtSearch.setText(keyword)
            getSearchSuggestion()
        }
    }


    override fun inject() {
    }

    override fun setClickListener() {
        tvCancelSearch.setOnClickListener {
//            searchSuggestionCallback?.onCancelSearch( keyword)
            KeyboardUtils.hideKeyboard(mContext, edtSearch)
            EventBus.getDefault().post(Done(keyword, contentType, null))
            fragmentManager?.popBackStack()
        }


        edtSearch.setOnFocusChangeListener { view, b ->
            if (b) {
                if (edtSearch.text.toString().isNotEmpty()) {
                    layoutSearchSuggestion.visibility = View.VISIBLE
                } else {
                    layoutSearchSuggestion.visibility = View.GONE
                }
            }
        }

        layoutKeyword.setOnClickListener {
            clickLayoutKeyword()
        }

        imgCloseSearch.setOnClickListener {
            KeyboardUtils.hideKeyboard(mContext, edtSearch)
            edtSearch.setText("")
            keyword = ""
//            searchSuggestionCallback?.onCancelSearch( keyword)
            fragmentManager?.popBackStack()
        }
    }

    private fun clickLayoutKeyword(){
        try {
            if (!isFromResultPage) {
                when (contentType){
                    SearchSuggestionForSpecificContentActivity.Type.VIDEO -> {
                        ResultSearchVideoActivity.openScreen(mContext, edtSearch.text.toString(), null, null)
                    }

                    SearchSuggestionForSpecificContentActivity.Type.NEWS -> {
//                            addFragment(ResultSearchNewsFragment(edtSearch.text.toString(), null, null))
                        ResultSearchNewsActivity.openScreen(mContext, edtSearch.text.toString(), null, null)
                    }

                    SearchSuggestionForSpecificContentActivity.Type.IMAGE -> {
                        ResultSearchImagesActivity.openScreen(mContext, edtSearch.text.toString(), null, null)
                    }
                }
//                    fragmentManager?.popBackStack()
            } else {
                EventBus.getDefault().post(Done(keyword, contentType, null))
                fragmentManager?.popBackStack()
            }
        } catch (e: Exception) {
        }
    }

    override fun setObserver() {}

    @SuppressLint("CheckResult")
    private fun handleSearch() {
        RxTextView.afterTextChangeEvents(edtSearch)
            .skipInitialValue()
            .debounce(790, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                try {
                    keyword = edtSearch.text.toString()
                    if (edtSearch.text.toString().isEmpty()) {
                        layoutSearchSuggestion.visibility = View.GONE
                    } else {
                        getSearchSuggestion()

                    }
                } catch (e: Exception) {
                }
            }
    }

    private fun getSearchSuggestion() {
        //searchSuggestionViewModel?.getSearchSuggestion(edtSearch.text.toString(), regionId)
        searchSuggestionViewModel?.getSearchSuggestionForSpecificContent(
            edtSearch.text.toString(),
            "",
            contentType
        )
        layoutKeyword.tvSearchFollow.text = "Tìm kiếm theo \"" + edtSearch.text.toString() + "\"";
        setHighLightedText(layoutKeyword.tvSearchFollow, "\"" + edtSearch.text.toString() + "\"")
        layoutSearchSuggestion.visibility = View.VISIBLE
    }

    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val iHighLightText = SearchHighLightText()
        val highLightController = HighLightController(iHighLightText)
        highLightController.highLight(mContext!!, tv, textToHighlight)
    }

    private fun getSuggestionInRecentSearch(): ArrayList<SearchSuggestionResponse.Data.Item> {
        var result = ArrayList<SearchSuggestionResponse.Data.Item>()
        var recent = getRecentSearch()
        for (i in 0 until recent.size) {
            if (recent[i].toLowerCase().contains(edtSearch.text.toString().toLowerCase())) {
                var suggestion = SearchSuggestionResponse().Data().Item()
                suggestion.setTitle(recent[i])
                suggestion.setType("recent")
                result.add(suggestion)
            }
        }
        return result
    }


    private fun clearRecentSearch() {
        PreferenceUtil.getInstance(context).setValue(Constants.PrefKey.RECENT_SEARCH, "")
    }

    private fun getRecentSearch(): ArrayList<String> {
        val arrayListRecentSearchs: ArrayList<String>
        val json = PreferenceUtil.getInstance(context).getValue(Constants.PrefKey.RECENT_SEARCH, "")
        arrayListRecentSearchs = if (json.isEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson(
                json,
                object : TypeToken<ArrayList<String?>?>() {}.type
            )
        }

        arrayListRecentSearchs.reverse()

        return arrayListRecentSearchs;
    }


    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchSuggestionViewModel && null != o) {
            when (o) {
                is SearchSuggestionResponse -> {
                    var list = o.data.items
                    list.addAll(0, getSuggestionInRecentSearch())
                    searchSuggestionKeyWordAdapter?.setKeyword(edtSearch.text.toString())
                    searchSuggestionKeyWordAdapter?.setData(list)
                }

                is ErrorResponse -> {
                    val responseError = o
                    try {
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    interface ClickSuggestion {
        fun onClickSuggestion(
            searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?,
            location: Location?
        )
    }

    interface CancelSearch {
        fun onCancelSearch(location: Location?, keyword: String?)
    }

    interface ClickRegionItem {
        fun onClickRegion(location: Location?, keyword: String?)
    }

    interface ClickLayoutKeyword {
        fun onClickLayoutKeyword(location: Location?, keyword: String?)
    }

    interface SearchSuggestionCallback {
        fun onClickSuggestion(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?)
        fun onCancelSearch(keyword: String?)
        fun onClickRegion(keyword: String?)
        fun onClickLayoutKeywordkeyword(keyword: String?)
    }

    public class Done {
        public var keyword: String? = null
        public var type: String? = null
        public var searchKeywordSuggestion: SearchSuggestionResponse.Data.Item? = null

        constructor(
            keyword: String?,
            type: String?,
            searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?
        ) {
            this.keyword = keyword
            this.type = type
            this.searchKeywordSuggestion = searchKeywordSuggestion
        }
    }
}