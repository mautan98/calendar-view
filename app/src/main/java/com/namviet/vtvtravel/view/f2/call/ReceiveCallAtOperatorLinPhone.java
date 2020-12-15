package com.namviet.vtvtravel.view.f2.call;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.namviet.vtvtravel.R;
import com.namviet.vtvtravel.databinding.F2FragmentReceiverCallOperatorBinding;
import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
import com.namviet.vtvtravel.f2base.base.BaseFragment;
import com.namviet.vtvtravel.service.LinphoneService;

import org.linphone.core.Call;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.ProxyConfig;
import org.linphone.core.RegistrationState;
import org.linphone.core.tools.Log;

import java.util.ArrayList;

public class ReceiveCallAtOperatorLinPhone extends BaseActivityNew<F2FragmentReceiverCallOperatorBinding> {
    private CoreListenerStub mCoreListener;

    @Override
    public int getLayoutRes() {
        return R.layout.f2_fragment_receiver_call_operator;
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
        setName();
        mCoreListener = new CoreListenerStub() {
            @Override
            public void onCallStateChanged(Core core, Call call, Call.State state, String message) {
//                if (state == Call.State.End || state == Call.State.Released) {
//                    finish();
//                } else if (state == Call.State.IncomingReceived) {
//
//                } else if (state == Call.State.Connected) {
//                    CallingAtOperatorLinPhone.startScreen(ReceiveCallAtOperatorLinPhone.this);
//                }

                switch (state){
                    case End:
                    case Released:
                        finish();
                        break;
                    case Connected:
                        CallingAtOperatorLinPhone.startScreen(ReceiveCallAtOperatorLinPhone.this);
                    case Error:
                        finish();
                        break;

                }

            }
        };
    }


    private void setName() {
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

    @Override
    public void setClick() {
        getBinding().btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinphoneService.acceptCall();
                finish();
            }
        });

        getBinding().btnHangup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinphoneService.declineCall();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            LinphoneService.declineCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
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

    @Override
    public BaseFragment initFragment() {
        return null;
    }

    public static void startScreen(Context context) {
        Intent intent = new Intent(context, ReceiveCallAtOperatorLinPhone.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAndRequestCallPermissions();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        // Callback for when permissions are asked to the user
        for (int i = 0; i < permissions.length; i++) {
            Log.i(
                    "[Permission] "
                            + permissions[i]
                            + " is "
                            + (grantResults[i] == PackageManager.PERMISSION_GRANTED
                            ? "granted"
                            : "denied"));

            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){

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
        Log.i(
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
            Log.i("[Permission] Asking for record audio");
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

        }
    }
}
