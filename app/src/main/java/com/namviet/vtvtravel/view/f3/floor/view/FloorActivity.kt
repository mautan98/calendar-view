package com.namviet.vtvtravel.view.f3.floor.view

import android.content.Context
import android.content.Intent
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F2ActivityFloorBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment

class FloorActivity : BaseActivityNew<F2ActivityFloorBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.f2_activity_floor
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {
    }

    override fun doAfterOnCreate() {

    }

    override fun setClick() {

    }

    override fun initFragment(): BaseFragment<*> {
        return FloorFragment()
    }

    companion object{
        fun openActivity(context: Context) {
            var intent = Intent(context, FloorActivity::class.java)
            context.startActivity(intent)
        }
    }
}