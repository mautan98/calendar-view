package com.namviet.vtvtravel.view.f2.call;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2ActivityCallingAtOperatorLinphoneBinding;
import com.namviet.vtvtravel.databinding.F2FragmentReceiverCallOperatorBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.service.LinphoneService;

import org.linphone.core.Call;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.tools.Log;

import java.util.ArrayList;

public class CallingAtOperatorLinPhone extends BaseActivityNew<F2ActivityCallingAtOperatorLinphoneBinding> {
    private CoreListenerStub mCoreListener;
    private CountDownTimer countDownTimer;
    private AudioManager audioManager;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_calling_at_operator_linphone;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void doAfterOnCreate() {
        setUp();
        setName();
        mCoreListener = new CoreListenerStub() {
            @Override
            public void onCallStateChanged(Core core, Call call, Call.State state, String message) {
                if (state == Call.State.End || state == Call.State.Released) {
                    finish();
                }



            }
        };

        startCount();
    }


    private void setName(){
        try {
            Core core = LinphoneService.getCore();
            if (core.getCallsNb() > 0) {
                Call call = core.getCurrentCall();
                if (call == null) {
                    // Current call can be null if paused for example
                    call = core.getCalls()[0];
                }

                try {
                    getBinding().tvPhone.setText("0"+call.getRemoteAddress().getDisplayName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpeakerphoneOn(boolean on) {
        boolean wasOn = audioManager.isSpeakerphoneOn();
        if (wasOn == on) {
            return;
        }
        audioManager.setSpeakerphoneOn(on);
    }

    private void setUp(){
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void setClick() {

        getBinding().btnHangup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Core core = LinphoneService.getCore();
                    if (core.getCallsNb() > 0) {
                        Call call = core.getCurrentCall();
                        if (call == null) {
                            // Current call can be null if paused for example
                            call = core.getCalls()[0];
                        }
                        call.terminate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        getBinding().btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSpeakerphoneOn(false);
                getBinding().btnSoundDisabled.setVisibility(View.VISIBLE);
                getBinding().btnSound.setVisibility(View.GONE);
            }
        });

        getBinding().btnSoundDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSpeakerphoneOn(true);
                getBinding().btnSoundDisabled.setVisibility(View.GONE);
                getBinding().btnSound.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            Core core = LinphoneService.getCore();
            if (core.getCallsNb() > 0) {
                Call call = core.getCurrentCall();
                if (call == null) {
                    // Current call can be null if paused for example
                    call = core.getCalls()[0];
                }
                call.terminate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            countDownTimer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    public BaseFragment initFragment() {
        return null;
    }

    public static void startScreen(Context context){
        Intent intent = new Intent(context, CallingAtOperatorLinPhone.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            LinphoneService.getCore().addListener(mCoreListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        try {
            LinphoneService.getCore().removeListener(mCoreListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
                + twoDigitString(seconds);
    }

    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    int seconds = 0;

    private void startCount() {
        if (countDownTimer != null) {
            seconds = 0;
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(30000000, 1000) {

            public void onTick(long millisUntilFinished) {
                seconds = seconds + 1000;
//                if (canCount) {
                    runOnUiThread(() -> getBinding().tvStatus.setText(formatMilliSecondsToTime(seconds)));

//                }
            }

            public void onFinish() {
            }
        }.start();
    }
}
