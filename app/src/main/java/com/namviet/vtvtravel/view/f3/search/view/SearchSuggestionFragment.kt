package com.namviet.vtvtravel.view.f3.search.view

import android.annotation.SuppressLint
import android.view.KeyEvent
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
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionMainFragment
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_layout_keyword.*
import kotlinx.android.synthetic.main.f2_layout_keyword.view.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.*
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.imgCloseSearch
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.layoutRegion
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.rclSearchSuggestion
import kotlinx.android.synthetic.main.f3_fragment_search_suggestion.tvRegion
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class SearchSuggestionFragment(private var keyword: String? = null, private var location: Location? = null, private var locationsMain: ArrayList<Location>? = null, private var clickRegion: Boolean = false, private var searchSuggestionCallback: SearchSuggestionCallback? = null) : BaseFragment<F3FragmentSearchSuggestionBinding?>(), Observer {

    private var searchSuggestionKeyWordAdapter: SearchSuggestionKeyWordAdapter? = null

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
                    KeyboardUtils.hideKeyboard(mActivity, edtSearch)
                    mActivity.onBackPressed()
                    searchSuggestionCallback?.onClickSuggestion(searchKeywordSuggestion, location)


                } catch (e: Exception) {
                }
            }

        })
        rclSearchSuggestion.adapter = searchSuggestionKeyWordAdapter


        checkKeyword()
        checkClickRegion()
        checkListLocation()
        handleSearch()

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                if (edtSearch.text.isNotEmpty()) {
                    var keyword = edtSearch.text.toString()
                    KeyboardUtils.hideKeyboard(mActivity, edtSearch)
                    mActivity.onBackPressed()
//                    searchSuggestionCallback?.onClickRegion(location, keyword)  comment dòng này và thay bằng dòng dưới bởi vì khi user nhấn tìm kiếm tại bàn phím quá nhanh thì keyword chưa kip tạo vì có delay
                    searchSuggestionCallback?.onClickRegion(location, keyword)
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
            layoutSearchSuggestion.visibility = View.GONE
        } else {
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
            searchSuggestionCallback?.onCancelSearch(location, keyword)
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
            mActivity.onBackPressed()
        }

        layoutRegion.setOnClickListener {
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
            edtSearch.clearFocus()
//            layoutSearchRegion.visibility = View.VISIBLE
//            layoutSearchSuggestion.visibility = View.GONE
            var chooseRegionMainFragment = ChooseRegionMainFragment();
            chooseRegionMainFragment.setData(locationsMain, object : ChooseRegionMainFragment.ChooseRegion{
                override fun clickRegion(location: Location?) {
                    tvRegion.text = location?.name
                    this@SearchSuggestionFragment.location = location
                    if(edtSearch.text.isNotEmpty()) {
                        mActivity.onBackPressed()
                        searchSuggestionCallback?.onClickRegion(location, keyword)
                    }
                }
            })
            addFragment(chooseRegionMainFragment)
        }

        edtSearch.setOnFocusChangeListener { view, b ->
            if(b){
                if(edtSearch.text.toString().isNotEmpty()) {
                    layoutSearchSuggestion.visibility = View.VISIBLE
                }else{
                    layoutSearchSuggestion.visibility = View.GONE
                }
            }
        }

        layoutKeyword.setOnClickListener {
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
            mActivity.onBackPressed()
            searchSuggestionCallback?.onClickRegion(location, keyword)
        }

        imgCloseSearch.setOnClickListener {
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
            edtSearch.setText("")
            keyword = ""
            searchSuggestionCallback?.onCancelSearch(location, keyword)
            mActivity.onBackPressed()
        }

//        binding!!.edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener{
//            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
//                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
//                    mActivity.onBackPressed()
//                    searchSuggestionCallback?.onClickRegion(location, keyword)
//                    return true;
//                }
//                return false;
//            }
//
//        })
    }

    override fun setObserver() {}

    @SuppressLint("CheckResult")
    private fun handleSearch() {
        RxTextView.afterTextChangeEvents(edtSearch)
                .skipInitialValue()
                .debounce(390, TimeUnit.MILLISECONDS)
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

                if(result.size >= 3){
                    break
                }
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

    interface ClickLayoutKeyword{
        fun onClickLayoutKeyword(location: Location?, keyword: String?)
    }

    interface SearchSuggestionCallback{
        fun onClickSuggestion(searchKeywordSuggestion: SearchSuggestionResponse.Data.Item?, location: Location?)
        fun onCancelSearch(location: Location?, keyword: String?)
        fun onClickRegion(location: Location?, keyword: String?)
        fun onClickLayoutKeyword(location: Location?, keyword: String?)
    }
}