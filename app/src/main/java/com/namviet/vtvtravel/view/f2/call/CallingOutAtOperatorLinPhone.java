package com.namviet.vtvtravel.view.f2.call;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.databinding.F2ActivityCallingOutOperatorLinphoneBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.service.LinphoneService;

import org.linphone.core.Address;
import org.linphone.core.Call;
import org.linphone.core.CallParams;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;

import java.util.ArrayList;

public class CallingOutAtOperatorLinPhone extends BaseActivityNew<F2ActivityCallingOutOperatorLinphoneBinding> {
    private CoreListenerStub mCoreListener;
    private CountDownTimer countDownTimer;
    private AudioManager audioManager;


    private String phone;
    @Override
    public int getLayoutRes() {
        return R.layout.f2_activity_calling_out_operator_linphone;
    }

    @Override
    public int getFrame() {
        return R.id.mainFrame;
    }

    @Override
    public void getDataFromIntent() {
        phone = getIntent().getStringExtra(Constants.IntentKey.DATA);
    }

    @Override
    public void doAfterOnCreate() {
//        setUp();
//        makeCall();
        checkAndRequestCallPermissions();
        getBinding().tvPhone.setText(phone.replaceFirst("1039", "0"));

    }

    private void setMute(boolean on){
        boolean wasOn = audioManager.isMicrophoneMute();
        if (wasOn == on) {
            return;
        }
        audioManager.setMicrophoneMute(on);
    }

    private void setSpeakerphoneOn(boolean on) {
        boolean wasOn = audioManager.isSpeakerphoneOn();
        if (wasOn == on) {
            return;
        }
        audioManager.setSpeakerphoneOn(on);
    }

    private boolean isMute(){
        return audioManager.isMicrophoneMute();
    }


    private void setUpMuteButton(){
        if(isMute()){
            getBinding().btnMic.setVisibility(View.GONE);
            getBinding().btnMicDisabled.setVisibility(View.VISIBLE);
        }else {
            getBinding().btnMic.setVisibility(View.VISIBLE);
            getBinding().btnMicDisabled.setVisibility(View.GONE);
        }
    }

    private void setUp(){
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mCoreListener = new CoreListenerStub() {
            @Override
            public void onCallStateChanged(Core core, Call call, Call.State state, String message) {
//                if (state == Call.State.End || state == Call.State.Released) {
//                    finish();
//                }else if (state == Call.State.Connected) {
//
//                }

                switch (state){
                    case End:
                    case Released:
                        finish();
                        break;
                    case Connected:
                        startCount();
                    case Error:
                        showToast("Cuộc gọi bị lỗi, mời bạn thử lại!");
                        break;

                }

                Log.e("stateeeeeeeeeee", String.valueOf(state.toInt())+"");
            }
        };

        setUpMuteButton();
    }

    private void showToast(String message){
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(CallingOutAtOperatorLinPhone.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeCall(){
        Core core = LinphoneService.getCore();
//        Address addressToCall = core.interpretUrl("1039988083551");
        Address addressToCall = core.interpretUrl(phone);
//        Address addressToCall = core.interpretUrl("1039983840677");
//        Address addressToCall = core.interpretUrl("1039985364208");
        CallParams params = core.createCallParams(null);
        params.enableVideo(false);

        if (addressToCall != null) {
            core.inviteAddressWithParams(addressToCall, params);
        }
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

        getBinding().btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMute(true);
                getBinding().btnMicDisabled.setVisibility(View.VISIBLE);
                getBinding().btnMic.setVisibility(View.GONE);
            }
        });

        getBinding().btnMicDisabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMute(false);
                getBinding().btnMicDisabled.setVisibility(View.GONE);
                getBinding().btnMic.setVisibility(View.VISIBLE);
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
            if(audioManager != null){
                setSpeakerphoneOn(false);
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

    public static void startScreen(Context context, String phone){
        Intent intent = new Intent(context, CallingOutAtOperatorLinPhone.class);
        intent.putExtra(Constants.IntentKey.DATA, phone);
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

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        // Callback for when permissions are asked to the user
        for (int i = 0; i < permissions.length; i++) {
            org.linphone.core.tools.Log.i(
                    "[Permission] "
                            + permissions[i]
                            + " is "
                            + (grantResults[i] == PackageManager.PERMISSION_GRANTED
                            ? "granted"
                            : "denied"));

            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                setUp();
                makeCall();
            }else {
                finish();
            }
        }
    }

    private void checkAndRequestCallPermissions() {
        ArrayList<String> permissionsList = new ArrayList<>();

        // Some required permissions needs to be validated manually by the user
        // Here we ask for record audio and camera to be able to make video calls with sound
        // Once granted we don't have to ask them again, but if denied we can
        int recordAudio =
                getPackageManager()
                        .checkPermission(Manifest.permission.RECORD_AUDIO, getPackageName());
        org.linphone.core.tools.Log.i(
                "[Permission] Record audio permission is "
                        + (recordAudio == PackageManager.PERMISSION_GRANTED
                        ? "granted"
                        : "denied"));
//        int camera =
//                getPackageManager().checkPermission(Manifest.permission.CAMERA, getPackageName());
//        Log.i(
//                "[Permission] Camera permission is "
//                        + (camera == PackageManager.PERMISSION_GRANTED ? "granted" : "denied"));

        if (recordAudio != PackageManager.PERMISSION_GRANTED) {
            org.linphone.core.tools.Log.i("[Permission] Asking for record audio");
            permissionsList.add(Manifest.permission.RECORD_AUDIO);
        }

//        if (camera != PackageManager.PERMISSION_GRANTED) {
//            Log.i("[Permission] Asking for camera");
//            permissionsList.add(Manifest.permission.CAMERA);
//        }

        if (permissionsList.size() > 0) {
            String[] permissions = new String[permissionsList.size()];
            permissions = permissionsList.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, 0);
        }else {
            setUp();
            makeCall();
        }
    }
}
