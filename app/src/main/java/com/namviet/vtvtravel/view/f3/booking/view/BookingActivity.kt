package com.namviet.vtvtravel.view.f3.booking.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.ActivityBookingBinding
import com.namviet.vtvtravel.databinding.ActivityCommingSoonBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.virtualswitchboard.VirtualSwitchBoardActivity

class BookingActivity : BaseActivityNew<ActivityBookingBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_booking
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
        return BookingFragment()
    }

    companion object{
        fun openActivity(context: Context) {
            var intent = Intent(context, BookingActivity::class.java)
            context.startActivity(intent)
        }
    }
}