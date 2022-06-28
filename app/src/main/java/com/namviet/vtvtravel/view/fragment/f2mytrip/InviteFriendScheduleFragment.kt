package com.namviet.vtvtravel.view.fragment.f2mytrip

import android.os.Bundle
import android.text.TextUtils
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentInviteScheduleBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.BaseResponse
import com.namviet.vtvtravel.ultils.DialogUtil
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
        binding.imvBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun setObserver() {
    }

    private fun validate(phoneNumber: String): Boolean {
        val phoneNumber = binding.edtPhoneNumber.text.toString().trim()
        if (TextUtils.isEmpty(phoneNumber)) {
            DialogUtil.showErrorDialog(
                requireContext().getString(R.string.error_title),
                requireContext().getString(R.string.close_title),
                "Số điện thoại không được để trống",
                childFragmentManager
            )
            return false
        } else if (ValidateUtils.isValidPhoneNumberNew(phoneNumber)){
            DialogUtil.showErrorDialog(
                requireContext().getString(R.string.error_title),
                requireContext().getString(R.string.close_title),
                "Số điện thoại định dạng chưa đúng",
                childFragmentManager
            )
            return false
        }
        return true
    }

    override fun update(o: Observable?, arg: Any?) {
        if (arg is BaseResponse) {
            val response = arg
            if (response.isSuccess) {
                val desc = requireContext().getString(
                    R.string.send_invite_to_phone_success,
                    binding.edtPhoneNumber.text.toString()
                )
                DialogUtil.showConfirmSuccessDialog(desc, "Ok", childFragmentManager)
            }
        } else if (arg is ErrorResponse) {
            val response = arg
            var desc = ""
            when(response.errorCode){
                CodePhone.PHONE_FORMAT_NOT_VALID -> {
                    desc = "Số điện thoại bạn nhập không đúng"
                }
                CodePhone.INVITE_DIFFERENT_NETWORK -> {
                    desc = "Số điện thoại bạn nhập không phải số của nhà mạng Viettel"
                }
            }
            DialogUtil.showErrorDialog(
                requireContext().getString(R.string.error_title),
                requireContext().getString(R.string.close_title),
                desc,
                childFragmentManager
            )
        }
    }
    object CodePhone{
        const val PHONE_FORMAT_NOT_VALID = "PHONE_FORMAT_NOT_VALID"
        const val INVITE_DIFFERENT_NETWORK = "INVITE_DIFFIRENT_NETWORK"
    }
}