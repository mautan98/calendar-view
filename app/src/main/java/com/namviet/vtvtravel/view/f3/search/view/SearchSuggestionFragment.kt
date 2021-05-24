package com.namviet.vtvtravel.view.f3.search.view

import android.annotation.SuppressLint
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F3FragmentSearchSuggestionBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.ultils.PreferenceUtil
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.search.viewmodel.SearchSuggestionViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_search.*
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.rclSearchSuggestion
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchSuggestionFragment : BaseFragment<F3FragmentSearchSuggestionBinding?>(), Observer {

    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null
    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()

    @Inject
    lateinit var searchSuggestionViewModel: SearchSuggestionViewModel
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_suggestion
    }

    override fun initView() {}
    override fun initData() {
        searchSuggestionViewModel.addObserver(this)


        searchSuggestionKeyWordAdapter = SearchSuggestionKeyWordAdapter(searchSuggestions, mActivity, object : SearchSuggestionKeyWordAdapter.ClickItem{
            override fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?) {
                try {
//                    edtKeyword.setText(searchKeywordSuggestion?.title)
//                    addRecentSearch(edtKeyword.text.toString())
//                    recentAdapter?.setData(getRecentSearch())
//                    addFragment(ResultSearchFragment(edtKeyword.text.toString(), regionId, searchKeywordSuggestion?.categoryCode))
//                    KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
//                    edtKeyword.clearFocus()
                } catch (e: Exception) {
                }
            }

        })
        rclSearchSuggestion.adapter = searchSuggestionKeyWordAdapter

        handleSearch()
    }
    override fun inject() {
        (mActivity.application as MyApplication).viewModelComponent.inject(this)
    }
    override fun setClickListener() {
        tvCancelSearch.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}

    @SuppressLint("CheckResult")
    private fun handleSearch(){
        RxTextView.afterTextChangeEvents(edtSearch)
                .skipInitialValue()
                .debounce(790, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    try {
                        if (edtSearch.text.toString().isEmpty()) {

                        } else {

//                            searchSuggestionViewModel?.getSearchSuggestion(edtSearch.text.toString(), regionId)
                            searchSuggestionViewModel.getSearchSuggestion(edtSearch.text.toString(), "")
                            layoutKeyword.tvSearchFollow.text = "Tìm kiếm theo \""+ edtSearch.text.toString()+"\"";
                            setHighLightedText(layoutKeyword.tvSearchFollow, "\""+ edtSearch.text.toString()+"\"")

//                            try {
//                                TrackingAnalytic.postEvent(TrackingAnalytic.SEARCH, TrackingAnalytic.getDefault(TrackingAnalytic.ScreenCode.SEARCH, TrackingAnalytic.ScreenTitle.SEARCH).setTerm(edtSearch.text.toString()).setScreen_class(this.javaClass.name))
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
                        }
                    } catch (e: Exception) {
                    }
                }
    }

    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val iHighLightText  = SearchHighLightText()
        val highLightController =  HighLightController(iHighLightText)
        highLightController.highLight(mActivity, tv, textToHighlight)
    }

    private fun getSuggestionInRecentSearch() : ArrayList<SearchSuggestionResponse.Data.Item>{
        var result = ArrayList<SearchSuggestionResponse.Data.Item>()
        var recent = getRecentSearch()
        for (i in 0 until recent.size){
            if(recent[i].toLowerCase().contains(edtKeyword.text.toString().toLowerCase())){
                var suggestion = SearchSuggestionResponse().Data().Item()
                suggestion.setTitle(recent[i])
                suggestion.setType("recent")
                result.add(suggestion)
            }
        }
        return result
    }


    private fun clearRecentSearch(){
        PreferenceUtil.getInstance(context).setValue(Constants.PrefKey.RECENT_SEARCH, "")
    }

    private fun getRecentSearch(): ArrayList<String> {
        val arrayListRecentSearchs: ArrayList<String>
        val json = PreferenceUtil.getInstance(context).getValue(Constants.PrefKey.RECENT_SEARCH, "")
        arrayListRecentSearchs = if (json.isEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson(json,
                    object : TypeToken<ArrayList<String?>?>() {}.type)
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
}