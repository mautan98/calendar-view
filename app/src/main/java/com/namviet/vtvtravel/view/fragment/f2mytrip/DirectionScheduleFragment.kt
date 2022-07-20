package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDirectionScheduleBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment

class DirectionScheduleFragment : BaseFragment<FragmentDirectionScheduleBinding>() {

    companion object {
        fun newInstance(): DirectionScheduleFragment {
            val args = Bundle()

            val fragment = DirectionScheduleFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentDirectionScheduleBinding
    private lateinit var map: GoogleMap

    override fun getLayoutRes(): Int {
        return R.layout.fragment_direction_schedule
    }

    override fun initView() {
        binding = getBinding()
        binding.mapView.onCreate(null)
        binding.mapView.onResume()
        binding.mapView.getMapAsync {
            map = it
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true

        }
    }

    override fun initData() {
    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }
}