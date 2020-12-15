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
import kotlinx.android.synthetic.main.f2_fragment_rule_lucky_wheel.*

class PlayRuleLuckyWheelFragment : BaseFragment<F2FragmentRuleLuckyWheelBinding?>() {
    private var token: String? = null
    private val chanel = "android"
    private val langCode = "vi"
    private val link: String? = null
    private val server = WSConfig.HOST_VQMM_GUIDE
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

        val account = MyApplication.getInstance().account
        if (null != account && account.isLogin) {
            token = account.token
            webView.loadUrl(genLink())
        } else { //            mActivity.onBackPressed();
        }
    }
    override fun initData() {



    }
    override fun inject() {}
    override fun setClickListener() {


    }
    override fun setObserver() {}

    private fun genLink(): String? {
        return server + "token=" + token + "&chanel=" + chanel + "&langCode=" + langCode
    }
}