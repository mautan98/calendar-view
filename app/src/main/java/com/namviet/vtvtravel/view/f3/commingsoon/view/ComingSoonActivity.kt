package com.namviet.vtvtravel.view.f3.commingsoon.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.ActivityCommingSoonBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity

class ComingSoonActivity : BaseActivityNew<ActivityCommingSoonBinding>() {
    private var type: String? = null

    override fun getLayoutRes(): Int {
        return R.layout.activity_comming_soon
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {
        type = intent.getStringExtra(Constants.IntentKey.DATA)
    }

    override fun doAfterOnCreate() {

    }

    override fun setClick() {

    }

    override fun initFragment(): BaseFragment<*> {
        return ComingSoonFragment(type)
    }

    companion object{
        fun openActivity(context: Context, type: String) {
            var intent = Intent(context, ComingSoonActivity::class.java)
            intent.putExtra(Constants.IntentKey.DATA, type)
            context.startActivity(intent)
        }
    }
}