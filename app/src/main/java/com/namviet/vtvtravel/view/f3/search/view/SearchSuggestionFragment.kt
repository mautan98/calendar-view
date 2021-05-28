package com.namviet.vtvtravel.view.f3.search.view

import android.annotation.SuppressLint
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.baseapp.utils.KeyboardUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter
import com.namviet.vtvtravel.adapter.f2search.SearchSuggestionKeyWordAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F3FragmentSearchSuggestionBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnBackSearchSuggestion
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse
import com.namviet.vtvtravel.response.f2searchmain.SearchSuggestionResponse
import com.namviet.vtvtravel.ultils.PreferenceUtil
import com.namviet.vtvtravel.ultils.highlight.HighLightController
import com.namviet.vtvtravel.ultils.highlight.SearchHighLightText
import com.namviet.vtvtravel.view.f3.search.viewmodel.SearchSuggestionViewModel
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.rclLocation
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.rclSearchSuggestion
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class SearchSuggestionFragment(private var clickSuggestion: ClickSuggestion? = null, private var keyword: String? = null, private var location: Location? = null, private var locationsMain: ArrayList<Location>? = null, private var clickRegion: Boolean = false, private var cancelSearch: CancelSearch? = null, private var clickRegionItem: ClickRegionItem? = null) : BaseFragment<F3FragmentSearchSuggestionBinding?>(), Observer {

    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null
    private var searchAllLocationAdapter: SearchAllLocationAdapter? = null

    private var searchSuggestions: ArrayList<SearchSuggestionResponse.Data.Item>? = ArrayList()

    @Inject
    lateinit var searchSuggestionViewModel: SearchSuggestionViewModel

    @Inject
    lateinit var viewModel: SearchBigLocationViewModel
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_suggestion
    }

    override fun initView() {}
    override fun initData() {
        searchSuggestionViewModel.addObserver(this)
        viewModel.addObserver(this)

        searchSuggestionKeyWordAdapter = SearchSuggestionKeyWordAdapter(searchSuggestions, mActivity, object : SearchSuggestionKeyWordAdapter.ClickItem {
            override fun onClickItem(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?) {
                try {
//                    edtKeyword.setText(searchKeywordSuggestion?.title)
//                    addRecentSearch(edtKeyword.text.toString())
//                    recentAdapter?.setData(getRecentSearch())
//                    addFragment(ResultSearchFragment(edtKeyword.text.toString(), regionId, searchKeywordSuggestion?.categoryCode))
//                    KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
//                    edtKeyword.clearFocus()
                    mActivity.onBackPressed()
                    clickSuggestion?.onClickSuggestion(searchKeywordSuggestion, location)


                } catch (e: Exception) {
                }
            }

        })
        rclSearchSuggestion.adapter = searchSuggestionKeyWordAdapter


        searchAllLocationAdapter = SearchAllLocationAdapter(mActivity, locationsMain, SearchAllLocationAdapter.ClickItem { location ->
            tvRegion.text = location?.name
            this.location = location
            if(edtSearch.text.isNotEmpty()) {
                mActivity.onBackPressed()
                clickRegionItem?.onClickRegion(location, keyword)
            }

        })
        rclLocation.adapter = searchAllLocationAdapter

        checkKeyword()
        checkClickRegion()
        checkListLocation()
        handleSearch()

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (edtSearch.text.isNotEmpty()) {
//                    addRecentSearch(edtKeyword.text.toString())
//                    recentAdapter?.setData(getRecentSearch())
//                    addFragment(ResultSearchFragment(edtKeyword.text.toString(), regionId, ""))
//                    KeyboardUtils.hideKeyboard(mActivity, edtKeyword)
//                    edtKeyword.clearFocus()
                    KeyboardUtils.hideKeyboard(mActivity, edtSearch)
                }
                true
            } else {
                false
            }
        }
    }

    private fun search() {

    }

    private fun focusSearch(){
        edtSearch.requestFocus()
        KeyboardUtils.showKeyboard(mActivity, edtSearch)
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

    private fun checkClickRegion() {
        if (clickRegion) {
            layoutSearchRegion.visibility = View.VISIBLE
            layoutSearchSuggestion.visibility = View.GONE
        } else {
            layoutSearchRegion.visibility = View.GONE
            layoutSearchSuggestion.visibility = View.VISIBLE
            focusSearch()
        }
        checkLocation()
    }

    private fun checkLocation(){
        if (location != null){
            tvRegion.text = location?.name
        }else{
            tvRegion.text = "Tất cả"
        }
    }

    private fun checkListLocation(){
        if(locationsMain.isNullOrEmpty()){
            locationsMain = ArrayList()
            viewModel.getAllLocation()
        }
    }

    override fun inject() {
        (mActivity.application as MyApplication).viewModelComponent.inject(this)
    }

    override fun setClickListener() {
        tvCancelSearch.setOnClickListener {
            cancelSearch?.onCancelSearch(location, keyword)
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
            mActivity.onBackPressed()
        }

        layoutRegion.setOnClickListener {
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
            edtSearch.clearFocus()
            layoutSearchRegion.visibility = View.VISIBLE
            layoutSearchSuggestion.visibility = View.GONE
        }

        edtSearch.setOnFocusChangeListener { view, b ->
            if(b){
                layoutSearchRegion.visibility = View.GONE
                layoutSearchSuggestion.visibility = View.VISIBLE
            }
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
        searchSuggestionViewModel.getSearchSuggestion(edtSearch.text.toString(), "")
        layoutKeyword.tvSearchFollow.text = "Tìm kiếm theo \"" + edtSearch.text.toString() + "\"";
        setHighLightedText(layoutKeyword.tvSearchFollow, "\"" + edtSearch.text.toString() + "\"")
        layoutSearchSuggestion.visibility = View.VISIBLE
    }

    public fun setHighLightedText(tv: TextView, textToHighlight: String) {
        val iHighLightText = SearchHighLightText()
        val highLightController = HighLightController(iHighLightText)
        highLightController.highLight(mActivity, tv, textToHighlight)
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
            Gson().fromJson(json,
                    object : TypeToken<ArrayList<String?>?>() {}.type)
        }

        arrayListRecentSearchs.reverse()

        return arrayListRecentSearchs;
    }


    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchBigLocationViewModel && null != o) {
            when (o) {
                is AllLocationResponse -> {
                    locationsMain?.clear()
                    locationsMain?.addAll(o.data as ArrayList<Location>)
                    searchAllLocationAdapter?.notifyDataSetChanged()
                }
                is LocationResponse -> {
                    tvRegion.text = o.data.name
                }
                is ErrorResponse -> {
                    val responseError = o
                }
            }
        } else if (observable is SearchSuggestionViewModel && null != o) {
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
        fun onClickSuggestion(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?, location: Location?)
    }

    interface CancelSearch{
        fun onCancelSearch(location: Location?, keyword: String?)
    }

    interface ClickRegionItem{
        fun onClickRegion(location: Location?, keyword: String?)
    }
}