package com.namviet.vtvtravel.view.f2.virtualswitchboard

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F2ActivityVirtualSwitchBoardBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.MyGiftActivity

import com.namviet.vtvtravel.view.f2.virtualswitchboard.page.OnWaitingCountUpdateListener
import com.namviet.vtvtravel.view.f2.OperatorInformationActivity
import com.namviet.vtvtravel.view.f2.virtualswitchboard.page.VirtualTicketPageFragment
import kotlinx.android.synthetic.main.f2_activity_virtual_switch_board.*


class VirtualSwitchBoardActivity : BaseActivityNew<F2ActivityVirtualSwitchBoardBinding>(), OnWaitingCountUpdateListener {

    companion object {
        var ticketType : String = ""

        fun openActivity(context: Context, ticketType : String) {
            var intent = Intent(context, VirtualSwitchBoardActivity::class.java)
            intent.putExtra(Constants.IntentKey.DATA, ticketType)
            context.startActivity(intent)
        }

        val TRAVEL_TYPE = "";
        val BOOKING_TYPE = "booking";
    }

    override fun getLayoutRes(): Int {
        return R.layout.f2_activity_virtual_switch_board
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {
        ticketType = if (intent.getStringExtra(Constants.IntentKey.DATA) == null) "" else intent.getStringExtra(Constants.IntentKey.DATA)
    }

    override fun doAfterOnCreate() {
        try {
            var account = MyApplication.getInstance().account;
            Glide.with(this).load(account.imageProfile).into(imgAvatar)
        } catch (e: Exception) {
        }
        view_pager.offscreenPageLimit = 10

        try {
            (view_pager.getChildAt(0) as RecyclerView).layoutManager?.isItemPrefetchEnabled = true
            (view_pager.getChildAt(0) as RecyclerView).setItemViewCacheSize(4)
        } catch (e: Exception) {

        }
        view_pager.isUserInputEnabled = false // tắt scroll viewpager
        view_pager.adapter = VirtualSwitchBoardPagerAdapter(supportFragmentManager, lifecycle, this@VirtualSwitchBoardActivity)
        TabLayoutMediator(binding.tabLayout, view_pager) { tab, position ->
            run {
                tab.setCustomView(R.layout.f2_tab_virtual_call)
                when (position) {
                    0 -> {
                        tab.setText("CHỜ XỬ LÝ")
                        tab.customView?.findViewById<TextView>(android.R.id.text1)?.typeface = ResourcesCompat.getFont(this@VirtualSwitchBoardActivity, R.font.roboto_bold)
                    }
                    1 -> {
                        tab.setText("ĐANG XỬ LÝ")
                        tab.customView?.findViewById<TextView>(R.id.tv_number)?.visibility = View.GONE
                    }
                    2 -> {
                        tab.setText("CHỜ PHẢN HỒI")
                        tab.customView?.findViewById<TextView>(R.id.tv_number)?.visibility = View.GONE
                    }
                    3 -> {
                        tab.setText("HOÀN THÀNH")
                        tab.customView?.findViewById<TextView>(R.id.tv_number)?.visibility = View.GONE
                    }
                    else -> tab.setText("")
                }
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.isSelected = true
                tab?.customView
                        ?.findViewById<TextView>(android.R.id.text1)
                        ?.typeface = ResourcesCompat.getFont(this@VirtualSwitchBoardActivity, R.font.roboto_bold)

                view_pager.requestLayout()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.isSelected = false
                tab?.customView
                        ?.findViewById<TextView>(android.R.id.text1)
                        ?.typeface = ResourcesCompat.getFont(this@VirtualSwitchBoardActivity, R.font.roboto_regular)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        if(ticketType == TRAVEL_TYPE) {
            binding.tvHeader.text = "Tổng đài Du Lịch Việt Nam"
        }else{
            binding.tvHeader.text = "Tổng đài Booking"
        }

    }

    override fun setClick() {
        imgAvatar.setOnClickListener {
            OperatorInformationActivity.startScreen(this)
        }
    }

    override fun initFragment(): BaseFragment<*>? {
        return null
    }

    override fun onUpdate(type: Int, count: Int) {
        if(type==0) {
            if (tab_layout?.tabCount!! > 0) {
                val tab = tab_layout?.getTabAt(0)
                val textView: TextView? = tab?.customView?.findViewById(R.id.tv_number)
                if(count>0){
                    textView?.visibility = View.VISIBLE
                    textView?.text = count.toString()
                } else {
                    textView?.visibility = View.GONE
                }
            }
        }
    }

}

class VirtualSwitchBoardPagerAdapter(fragmentManager: FragmentManager,
                                     lifeCycle: Lifecycle,
                                     private val onWaitingCountUpdateListener: OnWaitingCountUpdateListener)
    : FragmentStateAdapter(fragmentManager, lifeCycle) {


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return VirtualTicketPageFragment.newInstance(position, if (position == 0) onWaitingCountUpdateListener else null)
    }

}
