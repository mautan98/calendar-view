package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.baseapp.utils.KeyboardUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter
import com.namviet.vtvtravel.adapter.f2biglocation.sub.SearchAllLocationAdapter2
import com.namviet.vtvtravel.databinding.F3LayoutSearchDestinationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnTouchRCLLocation
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.response.f2biglocation.AllLocationResponse
import com.namviet.vtvtravel.response.f2biglocation.LocationResponse
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f3_layout_search_destination.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Handler
import kotlin.collections.ArrayList

class ChooseRegionFragment : BaseFragment<F3LayoutSearchDestinationBinding?>(), Observer {
    private var viewModel: SearchBigLocationViewModel? = null
    private var locationsMain: ArrayList<Location>? = null
    private val locations: ArrayList<Location> = ArrayList()
    private var chooseRegion: ChooseRegion? = null
    private var searchAllLocationAdapter: SearchAllLocationAdapter2? = null
    private var mContext: Context? = null
    private var content: Content? = null
    private var location: Location? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun getLayoutRes(): Int {
        return R.layout.f3_layout_search_destination
    }

    override fun initView() {}
    override fun initData() {
        viewModel = SearchBigLocationViewModel()
        viewModel!!.addObserver(this)
        locations.addAll(locationsMain!!)
        searchAllLocationAdapter = SearchAllLocationAdapter2(
            mContext,
            locations,
            SearchAllLocationAdapter2.ClickItem { location ->
                this@ChooseRegionFragment.location = location
                btnAction.text = "Chọn"

            })
        rclCity.adapter = searchAllLocationAdapter

        rclCity.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                //                scrl.requestDisallowInterceptTouchEvent(true);
                EventBus.getDefault().post(OnTouchRCLLocation())
                return false
            }


        })
        handleSearch()
    }

    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnCloseFilter.setOnClickListener {
            assert(fragmentManager != null)
            fragmentManager!!.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(this@ChooseRegionFragment).commit()
        }

        binding!!.btnMyLocation.setOnClickListener {
            viewModel?.getLocation()
        }

        binding!!.btnAction.setOnClickListener {
            if (location != null) {
                mActivity.onBackPressed()
                content?.cityId = location?.id
                content?.cityName = location?.name
                chooseRegion?.clickRegion(content)
            } else {
                assert(fragmentManager != null)
                fragmentManager!!.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .remove(this@ChooseRegionFragment).commit()
            }
        }
    }

    public fun setData(
        content: Content?,
        locationsMain: ArrayList<Location>?,
        chooseRegion: ChooseRegion?
    ) {
        this.locationsMain = locationsMain
        this.chooseRegion = chooseRegion
        this.content = content

        try {
            for (i in 0 until locationsMain!!.size) {
                locationsMain[i].isSelected = false
            }
        } catch (e: Exception) {
        }
    }

    public interface ChooseRegion {
        fun clickRegion(content: Content?)
    }

    override fun setObserver() {}

    @SuppressLint("CheckResult")
    private fun handleSearch() {
        RxTextView.afterTextChangeEvents(binding!!.edtSearch)
            .skipInitialValue()
            .debounce(790, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                try {
//                    binding!!.rclLocation.visibility = View.VISIBLE
                    locations.clear()
                    for (i in locationsMain!!.indices) {
                        if (F2Util.removeAccent(locationsMain!![i].name.toLowerCase()).contains(
                                F2Util.removeAccent(
                                    binding!!.edtSearch.text.toString().toLowerCase()
                                )
                            )
                        ) {
                            locations.add(locationsMain!![i])
                        }
                    }
                    searchAllLocationAdapter!!.notifyDataSetChanged()
                } catch (e: Exception) {
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            android.os.Handler().postDelayed(Runnable {
                KeyboardUtils.hideKeyboard(mActivity, binding!!.edtSearch)
            }, 100)
        } catch (e: Exception) {
        }
    }

    public fun deleteFragment() {
        fragmentManager?.beginTransaction()
            ?.remove(this)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)?.commit()
    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is SearchBigLocationViewModel && null != o) {
//            if (o is AllLocationResponse) {
//                locationsMain = o.data
//                locations.addAll(locationsMain)
//                searchAllLocationAdapter!!.notifyDataSetChanged()
//            } else
            if (o is LocationResponse) {
                edtSearch.setText(o.data.name)
            } else if (o is ErrorResponse) {
                val responseError = o
                try {
                } catch (e: Exception) {
                }
            }
        }
    }
}