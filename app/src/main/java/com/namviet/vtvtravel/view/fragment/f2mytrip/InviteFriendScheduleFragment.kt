package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import android.text.TextUtils
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentInviteScheduleBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.ultils.ValidateUtils
import com.namviet.vtvtravel.view.fragment.f2mytrip.viewmodel.MyTripsViewModel
import java.util.*

class InviteFriendScheduleFragment : BaseFragment<FragmentInviteScheduleBinding>(), Observer {

    companion object {
        const val KEY_SCHEDULE_ID = "scheduleId"
        fun newInstance(scheduleId:String): InviteFriendScheduleFragment {
            val args = Bundle()
            args.putString(KEY_SCHEDULE_ID,scheduleId)
            val fragment = InviteFriendScheduleFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private var viewmodel:MyTripsViewModel? = null
    private var scheduleId:String? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_invite_schedule
    }

    override fun initView() {
        scheduleId = arguments?.getString(KEY_SCHEDULE_ID)
        viewmodel = MyTripsViewModel()
        viewmodel?.addObserver(this)
    }

    override fun initData() {
    }

    override fun inject() {

    }

    override fun setClickListener() {
        binding.btnSendInvite.setOnClickListener {
            val phoneNumber = binding.edtPhoneNumber.text.toString().trim()
            if (validate(phoneNumber)){
                scheduleId?.let { it -> viewmodel?.inviteSchedule(it,phoneNumber) }
            }
        }
    }

    override fun setObserver() {
    }

    private fun validate(phoneNumber: String): Boolean {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false
        } else if (ValidateUtils.isValidPhoneNumberInvite(phoneNumber)) {
            return false
        }
        return true
    }

    override fun update(o: Observable?, arg: Any?) {

    }
}