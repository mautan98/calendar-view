package com.namviet.vtvtravel.view.fragment.f2webview

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.api.WSConfig
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentMenuLuckyWheelBinding
import com.namviet.vtvtravel.databinding.F2FragmentRuleLuckyWheelBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import kotlinx.android.synthetic.main.f2_fragment_rule_lucky_wheel.*
import java.util.*

class PlayRuleLuckyWheelFragment : BaseFragment<F2FragmentRuleLuckyWheelBinding?>(), Observer {
    private var luckyWheelViewModel : LuckyWheelViewModel? = null
    private val server = WSConfig.API_VQMM_GUIDE
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_rule_lucky_wheel
    }

    override fun initView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.setGeolocationEnabled(true)
        webSettings.setSupportMultipleWindows(true) // This forces ChromeClient enabled.


        binding!!.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress == 100) { //...page is fully loaded.
// TODO - Add whatever code you need here based on web page load completion...
                    shimmer_view_container?.stopShimmer()
                    layoutLoading?.visibility = View.GONE
                }
            }
        }

        binding!!.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.toUpperCase() == "APP://EXIT") {
                    mActivity.onBackPressed()
                } else {
                    view.loadUrl(url)
                }
                return true
            }
        }

    }
    override fun initData() {

        luckyWheelViewModel = LuckyWheelViewModel()
        luckyWheelViewModel?.addObserver(this)
        luckyWheelViewModel?.getRuleOrPlayRuleLuckyWheel(server)

    }
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

    }
    override fun setObserver() {}
    override fun update(o: Observable?, arg: Any?) {
        shimmer_view_container?.stopShimmer()
        layoutLoading?.visibility = View.GONE
    }

}