package com.namviet.vtvtravel.view.fragment.f2webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.vqmm.VQMMAdapter
import com.namviet.vtvtravel.databinding.F2FragmentDetailDealWebviewBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.model.f2event.OnChooseVoucherToRoll
import com.namviet.vtvtravel.model.f2event.OnRegisterVipSuccess
import com.namviet.vtvtravel.response.f2wheel.WheelAreasResponse
import com.namviet.vtvtravel.response.f2wheel.WheelResultResponse
import com.namviet.vtvtravel.response.f2wheel.WheelRotateResponse
import com.namviet.vtvtravel.view.f2.HistoryLuckyWheelActivity
import com.namviet.vtvtravel.view.f2.TravelVoucherActivity
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import kotlinx.android.synthetic.main.f2_fragment_detail_deal_webview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList


class VQMMWebviewFragment : BaseFragment<F2FragmentDetailDealWebviewBinding?>(), Observer {
    var listIds: ArrayList<String> = ArrayList()

    var canSwipe : Boolean = true
    var canRoll: Boolean = true // true sau khi gọi service rotate thành công
    var isScroll: Boolean? = false
    var datas: ArrayList<WheelAreasResponse.Item>? = ArrayList();
    var scrollAdapter: VQMMAdapter? = null
    var viewModel: LuckyWheelViewModel? = null;


    private var mSoundPool: SoundPool? = null
    private var mSoundPoolMap: SparseArray<Int>? = null
    private var mAudioManager: AudioManager? = null
    private var isSoundOn: Boolean? = true

    var positionVoucherAward: Int = -1
    var wheelLogId: String? = null
    var wheelResultResponse: WheelResultResponse? = null
    var wheelAreasResponse: WheelAreasResponse? = null

    var targetPosition: Int = -1


    public fun setVoucherId(id : String){
        listIds.clear()
        listIds.add(id)
    }


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_detail_deal_webview
    }

    override fun initView() {
        viewModel = LuckyWheelViewModel()
        viewModel?.addObserver(this)
        viewModel?.wheelAreas("VTVTRAVEL", listIds)
        viewModel?.wheelResult("VTVTRAVEL", "ANDROID", "app")
    }

    override fun initData() {
        Handler().postDelayed(Runnable {

            try {
                tvScroll.isSelected = true
            } catch (e: Exception) {
            }
            scrollAdapter = VQMMAdapter(context, datas)
            rclScroll.layoutManager = LinearLayoutManager(mActivity, SpeedyLinearLayoutManager.VERTICAL, false)
            rclScroll.adapter = scrollAdapter

            setSound()
        }, 200)

//        mAudioManager = mActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
//        mSoundPool = SoundPool(100, AudioManager.STREAM_MUSIC, 0)
//
//        mSoundPoolMap = SparseArray()
//
//        for (i in 0 until 50) {
//            mSoundPoolMap!!.put(i, mSoundPool!!.load(mActivity, R.raw.spin_0, 1))
//        }
    }


    fun playSound(index: Int) {
//        try {
//            val streamVolume = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
//            if (isSoundOn!!) {
//                mSoundPool!!.play(mSoundPoolMap!![index], 18f, 18f, 1, 0, 1f)
//            }
//        } catch (e: Exception) {
//        }
    }

    fun setData(wheelAreasResponse: WheelAreasResponse) {

        datas?.clear()

        scrollAdapter?.notifyDataSetChanged()


        Handler().postDelayed({
            for (i in 0 until 20) {
                datas?.addAll(wheelAreasResponse.data)
            }

            scrollAdapter?.notifyDataSetChanged()
        }, 1000)


    }

    fun setSound() {
//        mAudioManager = mActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
//        mSoundPool = SoundPool(100, AudioManager.STREAM_MUSIC, 0)
//
//        mSoundPoolMap = SparseArray()
//
//        for (i in 0 until 200) {
//            mSoundPoolMap!!.put(i, mSoundPool!!.load(mActivity, R.raw.spin_0, 1))
//        }
    }

    override fun inject() {}

    @SuppressLint("ClickableViewAccessibility")
    override fun setClickListener() {

        btnLeaderBoard.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (!isScroll!!) {
                            highlight(v)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
//                        LitterPartLuckyWheelActivity.startScreen(mActivity)
                        addFragment(LeaderBoardLuckyWheelFragment())
                        run { unHighLight(v) }
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        if (!isScroll!!) {
                            unHighLight(v)
                        }
                    }
                }
                return true
            }
        })

        imgStart.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (!isScroll!!) {
                            highlight(v)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        canSwipe = false
                        imgFrame.isClickable = true
                        if (canRoll) {
                            if (!isScroll!! && wheelLogId != null) {
                                canRoll = false
                                viewModel?.wheelRotate(wheelLogId, "VTVTRAVEL", "1");

                            } else {
                                Toast.makeText(mActivity, "Vòng quay chưa sẵn sàng", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(mActivity, "Vòng quay chưa sẵn sàng", Toast.LENGTH_SHORT).show()
                        }

                        run { unHighLight(v) }
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        if (!isScroll!!) {
                            unHighLight(v)
                        }
                    }
                }
                return true
            }
        })
        imgChooseGift.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    highlight(v)
                }
                MotionEvent.ACTION_UP -> {
//                    datas?.clear()
//                    scrollAdapter?.notifyDataSetChanged()

//                    ChooseVoucherLuckyWheelActivity.startScreen(mActivity, listIds)

                    addFragment(SelectGiftLuckyWheelFragment())
                    run { unHighLight(v) }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    unHighLight(v)
                }
            }
            true
        }
        imgMyBonus.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    highlight(v)
                }
                MotionEvent.ACTION_UP -> {
//                    HistoryLuckyWheelActivity.startScreen(mActivity)
                    addFragment(LuckyWheelHistoriesFragment())
                    run { unHighLight(v) }
                }
            }
            true
        }

        btnBack.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        mActivity.onBackPressed()
                    }
                }
                return true
            }
        })
        btnMenu.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    highlight(v)
                }
                MotionEvent.ACTION_UP -> {
//                    MenuLuckyWheelActivity.startScreen(mActivity, listIds)
                    addFragment(MenuLuckyWheelFragment())
                    run { unHighLight(v) }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    unHighLight(v)
                }
            }
            true
        }

        btnVolumeOn.setOnClickListener {
            isSoundOn = false
            btnVolumeOn.visibility = View.GONE
            btnVolumeOff.visibility = View.VISIBLE
        }

        btnVolumeOff.setOnClickListener {
            isSoundOn = true
            btnVolumeOn.visibility = View.VISIBLE
            btnVolumeOff.visibility = View.GONE
        }

        rclScroll.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !canSwipe) {

                    scrollAdapter?.highLight(targetPosition)
                    if (isSoundOn!!) {
//                        var mediaPlayer = MediaPlayer.create(mActivity, R.raw.win_sound)
//                        mediaPlayer.start()
                    }
                    Handler().postDelayed({
                        isScroll = false
                    }, 1000)

                    when (wheelResultResponse?.data?.wheelAward?.code) {
                        "CHUC_MAY_MAN_LAN_SAU" -> {
                            showDialog(LuckyWheelDialog.Type.SAD_TYPE, wheelResultResponse?.data?.wheelAward?.content)
                        }

                        "VOUCHER" -> {
                            var receiverVoucherLuckyWheelDialog = ReceiverVoucherLuckyWheelDialog.newInstance(ReceiverVoucherLuckyWheelDialog.ClickButton {
                                TravelVoucherActivity.openScreen(mActivity, false, TravelVoucherActivity.OpenType.LIST, false)
                            }, wheelResultResponse?.data?.wheelAward?.name)
                            receiverVoucherLuckyWheelDialog.show(mActivity.supportFragmentManager, null)
                        }


                        "LOI_HAY_Y_DEP" -> {
                            showDialog(LuckyWheelDialog.Type.BEST_WISH_TYPE, wheelResultResponse?.data?.wheelAward?.content)
                        }

                        "THEM_LUOT" -> {
                            showDialog(LuckyWheelDialog.Type.ADD_TYPE, "Chúc mừng bạn đã quay vào ô thêm lượt")
                        }

                        "MAT_LUOT" -> {
                            showDialog(LuckyWheelDialog.Type.SAD_TYPE, "Bạn đã quay vào ô mất lượt")
                        }

                        "STIMULATE" -> {
                            showDialog(LuckyWheelDialog.Type.VIP_TYPE, wheelResultResponse?.data?.wheelAward?.content)
                        }

                    }


                    //Lấy giải thưởng tiếp theo
                    wheelLogId = null;
                    viewModel?.wheelResult("VTVTRAVEL", "ANDROID", "app")
                }


            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView!!, dx, dy)
                val manager = recyclerView!!.layoutManager
                val llm = manager as LinearLayoutManager
                val visiblePosition: Int = llm.findFirstCompletelyVisibleItemPosition()

                if (visiblePosition != -1) {
                    if (visiblePosition != positionScroll) {
//                    var playSoundThread = PlaySoundThread(context);
//                    playSoundThread.run()
//                    setupRingTone()
                        playSound(visiblePosition)
                        positionScroll = visiblePosition;
                    }
                }

            }


        })
    }

    var positionScroll: Int = 1000;

    private fun highlight(view: View) {
        try {
            view.background.setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP)
            view.invalidate()
        } catch (e: Exception) {
        }
    }

    private fun unHighLight(view: View) {
        view.background.clearColorFilter()
        view.invalidate()
    }

    override fun setObserver() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onRegVipSuccess(onRegisterVipSuccess: OnRegisterVipSuccess?) {
    }

    override fun update(observable: Observable?, o: Any?) {
        if (observable is LuckyWheelViewModel && null != o) {
            when (o) {
                is WheelAreasResponse -> {
                    val response = o as WheelAreasResponse
                    wheelAreasResponse = response
                    setData(o)
                }
                is WheelResultResponse -> {
                    val response = o as WheelResultResponse;
                    wheelResultResponse = response
                    tvTurnLeft.text = "(Còn " + response.data.turn + " lượt quay)"
                    wheelLogId = response.data.wheelLogId;
                }
                is WheelRotateResponse -> {
                    val response = o as WheelRotateResponse;
                    if (response.errorCode == "USER_PACKAGE_NOT_VIP") {
                        canRoll = true
//                        val notifiDialog = NotifiDialog.newInstance("Thông báo", "Mời đăng ký gói VIP \nĐể tận hưởng ưu đãi từ VTV Travel", "Đồng ý") { ServiceActivity.startScreen(mActivity) }
//                        notifiDialog.show(mActivity.supportFragmentManager, null)

                        showDialog(LuckyWheelDialog.Type.VIP_TYPE, "Đăng ký vip để tham gia vòng quay may mắn")
                    } else if (response.errorCode == "NO_TURN") {
                        canRoll = true
                        showDialog(LuckyWheelDialog.Type.NOT_ENOUGH_TYPE, "Rất tiếc, bạn đã hết lượt quay")
                    } else {
                        scrollAdapter?.resetHighLight()


                        for (i in 0 until datas!!.size) {
                            if (datas?.get(i)?.position == wheelResultResponse?.data?.wheelAward?.position) {
                                rclScroll.scrollToPosition(0)
                                Handler().postDelayed({
                                    canRoll = true
                                    targetPosition = wheelAreasResponse?.data?.size!! * 5 + i
                                    rclScroll.smoothScrollToPosition(targetPosition)
                                    Log.e("targetPosition", targetPosition.toString())
                                    isScroll = true
                                    positionScroll = 1000


//                                    //Lấy giải thưởng tiếp theo
//                                    wheelLogId = null;
//                                    viewModel?.wheelResult("VTVTRAVEL", "ANDROID", "app")
                                }, 10)
                                return
                            }
                        }

                        Toast.makeText(mActivity, "Có lỗi đã xảy ra, mời bạn thử lại sau", Toast.LENGTH_SHORT).show()

                    }

//                    Handler().postDelayed({ rclScroll.smoothScrollToPosition(50) }, 10)

                }
                is ErrorResponse -> {
                    try {
                        val responseError = o as ErrorResponse
                        when (responseError.codeToSplitCase) {
                            "wheelRotate" -> {
                                Toast.makeText(mActivity, "Vòng quay chưa sẵn sàng", Toast.LENGTH_SHORT).show()
                            }
                            "wheelResult" -> {

                            }
                            "wheelAreas" -> {

                            }
                        }

                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    @Subscribe
    public fun onChooseVoucher(onChooseVoucherToRoll: OnChooseVoucherToRoll) {

        listIds = onChooseVoucherToRoll.stringArrayList

        wheelLogId = null;

        viewModel?.wheelAreas("VTVTRAVEL", onChooseVoucherToRoll.stringArrayList)
        viewModel?.wheelResult("VTVTRAVEL", "ANDROID", "app")
    }


    private fun showDialog(type: Int, title: String?) {
        var luckyWheelDialog = LuckyWheelDialog.newInstance(LuckyWheelDialog.ClickButton {
            ServiceActivity.startScreen(mActivity)
        }, type, title)
        luckyWheelDialog.show(mActivity.supportFragmentManager, null)
    }


}