package com.namviet.vtvtravel.view.f3.smalllocation.view.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.baseapp.utils.KeyboardUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentMainPageSmallLocationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.newhome.ItemHomeService
import com.namviet.vtvtravel.view.f3.smalllocation.viewmodel.SmallLocationMainViewModel
import com.namviet.vtvtravel.view.fragment.f2smalllocation.SmallLocationFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SmallLocationMainPageFragment(private var dataMenu: ArrayList<ItemHomeService<*>.Item>? = null) : BaseFragment<F2FragmentMainPageSmallLocationBinding?>() {
    private var mainAdapter : MainAdapter? = null


    @Inject
    lateinit var smallLocationMainViewModel: SmallLocationMainViewModel


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_main_page_small_location
    }


    override fun initView() {
        binding?.smallLocationMainViewModel = smallLocationMainViewModel
    }
    override fun initData() {
        smallLocationMainViewModel.setStateFirst()


        mainAdapter = MainAdapter(childFragmentManager)
        vpContent.offscreenPageLimit = 10

        for(i in 0 until dataMenu!!.size){
            var smallLocationFragment = SmallLocationFragment(dataMenu?.get(i)?.link, dataMenu?.get(i)?.code, "");
            mainAdapter?.addFragment(smallLocationFragment, "")
        }
        vpContent.adapter = mainAdapter
        tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package))
        tabLayout.setupWithViewPager(vpContent)

        for(i in 0 until dataMenu!!.size){
            tabLayout.getTabAt(i)?.text = dataMenu?.get(i)?.name
        }

    }
    override fun inject() {
        (mActivity.application as MyApplication).viewModelComponent.inject(this)
    }
    @SuppressLint("CheckResult")
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        RxTextView.afterTextChangeEvents(edtSearch)
                .skipInitialValue()
                .debounce(790, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    try {

                    } catch (e: Exception) {
                    }
                }

        edtSearch.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                smallLocationMainViewModel.setStateSecond()
            }else{
                smallLocationMainViewModel.setStateFirst()
            }
         }

        tvCancelSearch.setOnClickListener {
            smallLocationMainViewModel.setStateFirst()
            edtSearch.clearFocus()
            KeyboardUtils.hideKeyboard(mActivity, edtSearch)
        }

    }
    override fun setObserver() {}


}