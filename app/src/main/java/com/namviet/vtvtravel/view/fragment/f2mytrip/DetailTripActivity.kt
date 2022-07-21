package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.content.Context
import android.content.Intent
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.ActivityDetailTripBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.MyTripActivity
import com.namviet.vtvtravel.view.fragment.f2mytrip.model.TripItem

class DetailTripActivity : BaseActivityNew<ActivityDetailTripBinding>() {

    companion object {

        fun startScreen(activity: Context, tripId: String) {
            val intent = Intent(activity, MyTripActivity::class.java)
            intent.apply { putExtra(DetailTripFragment.KEY_TRIP_ITEM, tripId) }
            activity.startActivity(intent)
        }
    }

    private var scheduleId: String? = null
    override fun getLayoutRes(): Int {
        return R.layout.activity_detail_trip
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    fun startScreen(activity: Context) {
        val intent = Intent(activity, MyTripActivity::class.java)
        activity.startActivity(intent)
    }

    override fun getDataFromIntent() {
        scheduleId= intent.getStringExtra(DetailTripFragment.KEY_TRIP_ITEM)
    }

    override fun doAfterOnCreate() {
    }

    override fun setClick() {

    }

    override fun initFragment(): BaseFragment<*> {
        val tripItem = TripItem()
        tripItem.id = scheduleId
        val detailTripFragment: DetailTripFragment = DetailTripFragment.newInstance(tripItem)
        detailTripFragment.setOnBackToTripsFragment(object :
            DetailTripFragment.OnBackFragmentListener {
            override fun onBackFragment() {
            }
        })
        return detailTripFragment
    }
}