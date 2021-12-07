package com.namviet.vtvtravel.view.f3.deal.view.listhotdeal

import android.content.Context
import android.content.Intent
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.ActivityListDealBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.model.Block

class ListHotDealActivity : BaseActivityNew<ActivityListDealBinding?>() {
    private var listBlock = ArrayList<Block>()
    private var positionClick = 0;
    override fun getLayoutRes(): Int {
        return R.layout.activity_list_deal
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {
        listBlock = intent.getSerializableExtra(Constants.IntentKey.DATA) as ArrayList<Block>
        positionClick = intent.getIntExtra("position", 0)
    }
    override fun setClick() {}
    override fun initFragment(): BaseFragment<*> {
        var listHotDealFragment = ListHotDealFragment();
        listHotDealFragment.setData(listBlock, positionClick)
        return listHotDealFragment
    }

    override fun doAfterOnCreate() {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.my_statusbar_color)
    }

    companion object {
        fun startScreen(activity: Context, listBlock: ArrayList<Block>?, position : Int) {
            val intent = Intent(activity, ListHotDealActivity::class.java)
            intent.putExtra(Constants.IntentKey.DATA, listBlock);
            intent.putExtra("position", position);
            activity.startActivity(intent)
        }
    }
}