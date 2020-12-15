package com.namviet.vtvtravel.view.f2;

//import android.Manifest;
//import android.app.Activity;
//import android.app.KeyguardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.giorgosneokleous.fullscreenintentexample.ActivityUtilsKt;
//import com.namviet.vtvtravel.R;
//import com.namviet.vtvtravel.config.Constants;
//import com.namviet.vtvtravel.databinding.F2ActivityCallBinding;
//import com.namviet.vtvtravel.f2base.base.BaseActivityNew;
//import com.namviet.vtvtravel.f2base.base.BaseFragment;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.webrtc.AudioSource;
//import org.webrtc.AudioTrack;
//import org.webrtc.Camera1Enumerator;
//import org.webrtc.CameraEnumerator;
//import org.webrtc.CameraVideoCapturer;
//import org.webrtc.DefaultVideoDecoderFactory;
//import org.webrtc.DefaultVideoEncoderFactory;
//import org.webrtc.EglBase;
//import org.webrtc.IceCandidate;
//import org.webrtc.Logging;
//import org.webrtc.MediaConstraints;
//import org.webrtc.MediaStream;
//import org.webrtc.PeerConnection;
//import org.webrtc.PeerConnectionFactory;
//import org.webrtc.SessionDescription;
//import org.webrtc.SurfaceTextureHelper;
//import org.webrtc.SurfaceViewRenderer;
//import org.webrtc.VideoCapturer;
//import org.webrtc.VideoSource;
//import org.webrtc.VideoTrack;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OperatorWebRTCCallActivity extends BaseActivityNew<F2ActivityCallBinding> implements View.OnClickListener, SignallingClient.SignalingInterface {
//
//    MediaPlayer mPlayer;
//    public static final int FROM_CALL = 0;
//    public static final int FROM_ANSWER = 1;
//
//
//    private String roomId;
//    private int fromWhere;
//
//    private boolean isVideoCall = true;
//
//    // partner information
//    private String partnerName;
//    private String partnerAvatar;
//    private int partnerId = 0;
//
//    PeerConnectionFactory peerConnectionFactory;
//    MediaConstraints audioConstraints;
//    MediaConstraints videoConstraints;
//    MediaConstraints sdpConstraints;
//    VideoSource videoSource;
//    VideoTrack localVideoTrack;
//    AudioSource audioSource;
//    AudioTrack localAudioTrack;
//    SurfaceTextureHelper surfaceTextureHelper;
//
//    SurfaceViewRenderer localVideoView;
//    SurfaceViewRenderer remoteVideoView;
//
//    ImageView hangup;
//    PeerConnection localPeer;
//    EglBase rootEglBase;
//
//    boolean gotUserMedia;
//    List<PeerConnection.IceServer> peerIceServers = new ArrayList<>();
//
//    final int ALL_PERMISSIONS_CODE = 1;
//
//    private static final String TAG = "CallActivity";
//
//    private CameraVideoCapturer cameraVideoCapturer;
//
//    private CountDownTimer countDownTimer;
//
//
//
//    private boolean canCount = false;
//    private boolean isStartedCount = false;
//    private AudioManager audioManager;
//    private Window wind;
//    private Handler handler;
//
//
//
//    @Override
//    public int getLayoutRes() {
//        return R.layout.f2_activity_call;
//    }
//
//    @Override
//    public int getFrame() {
//        return R.id.mainFrame;
//    }
//
//    @Override
//    public void getDataFromIntent() {
//        roomId = getIntent().getStringExtra(Constants.IntentKey.ROOM_ID);
//        fromWhere = getIntent().getIntExtra(Constants.IntentKey.FROM_WHERE, 0);
//        partnerName = getIntent().getStringExtra(Constants.IntentKey.PART_NAME);
//        isVideoCall = getIntent().getBooleanExtra(Constants.IntentKey.IS_VIDEO_CALL, true);
//        setDataForFirstTime();
//    }
//
//    private void setDataForFirstTime() {
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
////        binding.tvName.setText(partnerName);
////        if (isVideoCall) {
////            binding.viewIsCallVideoOrAudio.setVisibility(View.GONE);
////            binding.tvCountTime.setTextColor(ContextCompat.getColor(this, R.color.white));
////            binding.tvCallWhat.setText("Cuộc gọi video");
////            binding.swVideo.setChecked(true);
////            binding.btnSpeakerDisable.setVisibility(View.GONE);
////            binding.btnSpeakerEnable.setVisibility(View.VISIBLE);
////            setSpeakerphoneOn(true);
////        } else {
////            binding.swVideo.setChecked(false);
////            binding.btnSpeakerDisable.setVisibility(View.VISIBLE);
////            binding.btnSpeakerEnable.setVisibility(View.GONE);
////
////            setSpeakerphoneOn(false);
////            binding.tvCallWhat.setText("Cuộc gọi audio");
////            binding.viewIsCallVideoOrAudio.setVisibility(View.VISIBLE);
////            binding.tvCountTime.setTextColor(ContextCompat.getColor(this, R.color.black));
////        }
//
//        setUpHangupWhenTimeUp();
//    }
//
//    @Override
//    public void doAfterOnCreate() {
//        try {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ActivityUtilsKt.turnScreenOnAndKeyguardOff(OperatorWebRTCCallActivity.this);
//                }
//            }, 500);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
//        lock.disableKeyguard();
//
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, ALL_PERMISSIONS_CODE);
//        } else {
//            // all permissions already granted
//            start();
//        }
//    }
//
//    private void setUpRingtone() {
//        mPlayer = MediaPlayer.create(this, R.raw.ocean_rain);
//        mPlayer.start();
//    }
//
//
//    public void start() {
//        // keep screen on
//        if (fromWhere == SignallingClient.FROM_CALL) {
//            setUpRingtone();
//        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        initViews();
//        initVideos();
//        getIceServers();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SignallingClient.getInstance().init(OperatorWebRTCCallActivity.this, roomId, fromWhere, isVideoCall);
//            }
//        }, 1000);
//
////        SignallingClient.getInstance().setRoomId(roomId);c
//
//        //Initialize PeerConnectionFactory globals.
//        PeerConnectionFactory.InitializationOptions initializationOptions =
//                PeerConnectionFactory.InitializationOptions.builder(this)
//                        .createInitializationOptions();
//        PeerConnectionFactory.initialize(initializationOptions);
//
//        //Create a new PeerConnectionFactory instance - using Hardware encoder and decoder.
//        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
//        DefaultVideoEncoderFactory defaultVideoEncoderFactory = new DefaultVideoEncoderFactory(
//                rootEglBase.getEglBaseContext(),  /* enableIntelVp8Encoder */true,  /* enableH264HighProfile */true);
//        DefaultVideoDecoderFactory defaultVideoDecoderFactory = new DefaultVideoDecoderFactory(rootEglBase.getEglBaseContext());
//        peerConnectionFactory = PeerConnectionFactory.builder()
//                .setOptions(options)
//                .setVideoEncoderFactory(defaultVideoEncoderFactory)
//                .setVideoDecoderFactory(defaultVideoDecoderFactory)
//                .createPeerConnectionFactory();
//
//        //Now create a VideoCapturer instance.
//        VideoCapturer videoCapturerAndroid;
//        videoCapturerAndroid = createCameraCapturer(new Camera1Enumerator(false));
//
//
//        cameraVideoCapturer = (CameraVideoCapturer) videoCapturerAndroid;
//
//
//        //Create MediaConstraints - Will be useful for specifying video and audio constraints.
//        audioConstraints = new MediaConstraints();
//        videoConstraints = new MediaConstraints();
//
//        //Create a VideoSource instance
//        if (videoCapturerAndroid != null) {
//            surfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", rootEglBase.getEglBaseContext());
//            videoSource = peerConnectionFactory.createVideoSource(videoCapturerAndroid.isScreencast());
//            videoCapturerAndroid.initialize(surfaceTextureHelper, this, videoSource.getCapturerObserver());
//        }
//        localVideoTrack = peerConnectionFactory.createVideoTrack("100", videoSource);
//
//        //create an AudioSource instance
//        audioSource = peerConnectionFactory.createAudioSource(audioConstraints);
//        localAudioTrack = peerConnectionFactory.createAudioTrack("101", audioSource);
//
//        if (videoCapturerAndroid != null) {
//            videoCapturerAndroid.startCapture(1024, 720, 30);
//        }
//
//        localVideoView.setVisibility(View.VISIBLE);
//        // And finally, with our VideoRenderer ready, we
//        // can add our renderer to the VideoTrack.
//        localVideoTrack.addSink(localVideoView);
//
//        localVideoView.setMirror(true);
//        remoteVideoView.setMirror(true);
//
//        gotUserMedia = true;
//    }
//
//
//    private void initViews() {
//        hangup = findViewById(R.id.btnHangup);
//        localVideoView = findViewById(R.id.local_gl_surface_view);
//        remoteVideoView = findViewById(R.id.remote_gl_surface_view);
//        hangup.setOnClickListener(this);
//    }
//
//    private void initVideos() {
//        rootEglBase = EglBase.create();
//        localVideoView.init(rootEglBase.getEglBaseContext(), null);
//        remoteVideoView.init(rootEglBase.getEglBaseContext(), null);
//        localVideoView.setZOrderMediaOverlay(true);
//        remoteVideoView.setZOrderMediaOverlay(true);
//    }
//
//    private void getIceServers() {
//
//        PeerConnection.IceServer peerIceServer = PeerConnection.IceServer.builder("turn:103.21.148.54:3478")
//                .setUsername("namviet")
//                .setPassword("namvietpw@123")
//                .createIceServer();
//        peerIceServers.add(peerIceServer);
//
//    }
//
//    private VideoCapturer createCameraCapturer(CameraEnumerator enumerator) {
//        final String[] deviceNames = enumerator.getDeviceNames();
//
//        // First, try to find front facing camera
//        Logging.d(TAG, "Looking for front facing cameras.");
//        for (String deviceName : deviceNames) {
//            if (enumerator.isFrontFacing(deviceName)) {
//                Logging.d(TAG, "Creating front facing camera capturer.");
//                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
//
//                if (videoCapturer != null) {
//                    return videoCapturer;
//                }
//            }
//        }
//
//        // Front facing camera not found, try something else
//        Logging.d(TAG, "Looking for other cameras.");
//        for (String deviceName : deviceNames) {
//            if (!enumerator.isFrontFacing(deviceName)) {
//                Logging.d(TAG, "Creating other camera capturer.");
//                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
//
//                if (videoCapturer != null) {
//                    return videoCapturer;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public void showToast(final String msg) {
//        runOnUiThread(() -> Toast.makeText(OperatorWebRTCCallActivity.this, msg, Toast.LENGTH_SHORT).show());
//    }
//
//    public void onIceCandidateReceived(IceCandidate iceCandidate) {
//        //we have received ice candidate. We can set it to the other peer.
//        SignallingClient.getInstance().emitIceCandidateOfMine(iceCandidate, roomId);
//    }
//
//    private void gotRemoteStream(MediaStream stream) {
//        //we have remote video stream. add to the renderer.
//        final VideoTrack videoTrack = stream.videoTracks.get(0);
//        runOnUiThread(() -> {
//            try {
//                remoteVideoView.setVisibility(View.VISIBLE);
//                videoTrack.addSink(remoteVideoView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public void setText(final String msg) {
//        runOnUiThread(() -> getBinding().tvStatus.setText(msg));
//    }
//
//
//    private void createPeerConnection() {
//
//        PeerConnection.RTCConfiguration rtcConfig =
//                new PeerConnection.RTCConfiguration(peerIceServers);
//        // TCP candidates are only useful when connecting to a server that supports
//        // ICE-TCP.
//        rtcConfig.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.DISABLED;
//        rtcConfig.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
//        rtcConfig.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
//        rtcConfig.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY;
//        // Use ECDSA encryption.
//        rtcConfig.keyType = PeerConnection.KeyType.ECDSA;
//        localPeer = peerConnectionFactory.createPeerConnection(rtcConfig, new CustomPeerConnectionObserver("localPeerCreation") {
//            @Override
//            public void onIceCandidate(IceCandidate iceCandidate) {
//                super.onIceCandidate(iceCandidate);
//                onIceCandidateReceived(iceCandidate);
//            }
//
//            @Override
//            public void onAddStream(MediaStream mediaStream) {
//                showToast("Received Remote stream");
//                super.onAddStream(mediaStream);
//                gotRemoteStream(mediaStream);
//            }
//
//            @Override
//            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
//                super.onIceConnectionChange(iceConnectionState);
//                try {
//                    switch (iceConnectionState) {
//                        case NEW:
//                            showToast("New");
//                            setText("Đang tạo kết nối mới");
//                            canCount = false;
//                            break;
//                        case CLOSED:
//                            showToast("Close");
//                            canCount = false;
//                            break;
//                        case CHECKING:
//                            showToast("Checking");
//                            setText("Đang kiểm tra kết nối");
//                            canCount = false;
//                            break;
//                        case CONNECTED:
//                            showToast("Connected");
//                            canCount = true;
//                            if (!isStartedCount) {
//                                seconds = 0;
//                                isStartedCount = true;
//                            }
//                            break;
//                        case DISCONNECTED:
//                            showToast("Disconnected");
//                            setText("Mất kết nối");
//                            canCount = false;
//                            break;
//                        case FAILED:
//                            showToast("Fail");
//                            setText("Cuộc gọi bị lỗi");
//                            canCount = false;
//                            break;
//                        case COMPLETED:
//                            showToast("Completed");
//                            canCount = false;
//                            break;
//
//                    }
//                } catch (Exception e) {
//                    Log.e("", "");
//                }
//            }
//        });
//
//        addStreamToLocalPeer();
//    }
//
//    public int dpToPx(int dp) {
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//    }
//
//    private void updateVideoViews(final boolean remoteVisible) {
//        runOnUiThread(() -> {
//            ViewGroup.LayoutParams params = localVideoView.getLayoutParams();
//            if (remoteVisible) {
//                params.height = dpToPx(200);
//                params.width = dpToPx(100);
//                try {
//                    mPlayer.release();
//                } catch (Exception e) {
//
//                }
//                if (isVideoCall) {
////                    binding.layoutUserInfo.setVisibility(View.GONE);
//                }
//                startCount();
//            } else {
//                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            }
//            localVideoView.setLayoutParams(params);
//        });
//    }
//
//    private String formatMilliSecondsToTime(long milliseconds) {
//
//        int seconds = (int) (milliseconds / 1000) % 60;
//        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
//        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
//                + twoDigitString(seconds);
//    }
//
//    private String twoDigitString(long number) {
//
//        if (number == 0) {
//            return "00";
//        }
//
//        if (number / 10 == 0) {
//            return "0" + number;
//        }
//
//        return String.valueOf(number);
//    }
//
//    int seconds = 0;
//
//    private void startCount() {
//        if (countDownTimer != null) {
//            seconds = 0;
//            countDownTimer.cancel();
//        }
//        countDownTimer = new CountDownTimer(30000000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                seconds = seconds + 1000;
//                if (canCount) {
//                    runOnUiThread(() -> getBinding().tvStatus.setText(formatMilliSecondsToTime(seconds)));
//
//                }
//            }
//
//            public void onFinish() {
//            }
//        }.start();
//    }
//
//    private void addStreamToLocalPeer() {
//        //creating local mediastream
//        MediaStream stream = peerConnectionFactory.createLocalMediaStream("102");
//        stream.addTrack(localAudioTrack);
//        stream.addTrack(localVideoTrack);
//        localPeer.addStream(stream);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        wind = this.getWindow();
//        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//    }
//
//    @Override
//    public void setClick() {
//
//    }
//
//    @Override
//    public BaseFragment initFragment() {
//        return null;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == ALL_PERMISSIONS_CODE
//                && grantResults.length == 2
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//            // all permissions granted
//            start();
//        } else {
//            finish();
//        }
//    }
//
//    private void hangup() {
//        try {
//            if (localPeer != null) {
//                localPeer.close();
//            }
//            localPeer = null;
//            updateVideoViews(false);
//            finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void doCall() {
//        sdpConstraints = new MediaConstraints();
//        sdpConstraints.mandatory.add(
//                new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
//        sdpConstraints.mandatory.add(
//                new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));
//        localPeer.createOffer(new CustomSdpObserver("localCreateOffer") {
//            @Override
//            public void onCreateSuccess(SessionDescription sessionDescription) {
//                super.onCreateSuccess(sessionDescription);
//                localPeer.setLocalDescription(new CustomSdpObserver("localSetLocalDesc"), sessionDescription);
//                Log.d("onCreateSuccess", "SignallingClient emit ");
//                SignallingClient.getInstance().emitMessage(sessionDescription);
//                showToast("Kết nối thành công, đang thực hiện cuộc gọi");
//                SignallingClient.getInstance().emitMessageOfMine(sessionDescription, roomId);
//            }
//        }, sdpConstraints);
//    }
//
//    private void setSpeakerphoneOn(boolean on) {
//        boolean wasOn = audioManager.isSpeakerphoneOn();
//        if (wasOn == on) {
//            return;
//        }
//        audioManager.setSpeakerphoneOn(on);
//    }
//
//
//    private void doAnswer() {
//        localPeer.createAnswer(new CustomSdpObserver("localCreateAns") {
//            @Override
//            public void onCreateSuccess(SessionDescription sessionDescription) {
//                super.onCreateSuccess(sessionDescription);
//                localPeer.setLocalDescription(new CustomSdpObserver("localSetLocal"), sessionDescription);
//                SignallingClient.getInstance().emitMessage(sessionDescription);
//                SignallingClient.getInstance().emitMessageOfMine(sessionDescription, roomId);
//            }
//        }, new MediaConstraints());
//    }
//
//    public static void startScreen(Context activity, String roomId, int fromWhere, String partnerName, boolean isVideoCall) {
//        Intent intent = new Intent(activity, OperatorWebRTCCallActivity.class);
//        intent.putExtra(Constants.IntentKey.ROOM_ID, roomId);
//        intent.putExtra(Constants.IntentKey.FROM_WHERE, fromWhere);
//        intent.putExtra(Constants.IntentKey.PART_NAME, partnerName);
//        intent.putExtra(Constants.IntentKey.IS_VIDEO_CALL, isVideoCall);
//        activity.startActivity(intent);
//    }
//
//    private void setUpHangupWhenTimeUp(){
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    hangup.performClick();
//                } catch (Exception e) {
//
//                }
//            }
//        }, 45000);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public void onRemoteHangUp(String msg) {
//        showToast("Remote Peer hungup");
//        runOnUiThread(this::hangup);
//    }
//
//    @Override
//    public void onOfferReceived(JSONObject data) {
//        showToast("Received Offer");
//        runOnUiThread(() -> {
//            if (!SignallingClient.getInstance().isInitiator && !SignallingClient.getInstance().isStarted) {
//                onTryToStart();
//            }
//
//            try {
//                localPeer.setRemoteDescription(new CustomSdpObserver("localSetRemote"), new SessionDescription(SessionDescription.Type.OFFER, data.getString("sdp")));
//                doAnswer();
//                updateVideoViews(true);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    @Override
//    public void onAnswerReceived(JSONObject data) {
//        showToast("Received Answer");
//        try {
//            localPeer.setRemoteDescription(new CustomSdpObserver("localSetRemote"), new SessionDescription(SessionDescription.Type.fromCanonicalForm(data.getString("type").toLowerCase()), data.getString("sdp")));
//            updateVideoViews(true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onIceCandidateReceived(JSONObject data) {
//        try {
//            localPeer.addIceCandidate(new IceCandidate(data.getString("sdpMid"), data.getInt("sdpMLineIndex"), data.getString("sdp")));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onTryToStart() {
//        runOnUiThread(() -> {
////            if (!SignallingClient.getInstance().isStarted && localVideoTrack != null && SignallingClient.getInstance().isChannelReady) {
////                createPeerConnection();
////                SignallingClient.getInstance().isStarted = true;
////                if (SignallingClient.getInstance().isInitiator) {
////                    doCall();
////                }
////            }
//
//            if (!SignallingClient.getInstance().isStarted && localVideoTrack != null) {
//                createPeerConnection();
//                SignallingClient.getInstance().isStarted = true;
//                if (SignallingClient.getInstance().isInitiator) {
//                    doCall();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onWakeUp(JSONObject data) {
//
//    }
//
//    @Override
//    public void onHangup() {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        Toast.makeText(this, "destroy", Toast.LENGTH_SHORT).show();
//        try {
//            mPlayer.release();
//        } catch (Exception e) {
//
//        }
//        SignallingClient.getInstance().emitHangup(roomId);
//        SignallingClient.getInstance().close();
//
//
//        ////////////////////////////
//        if (peerConnectionFactory != null) {
//            peerConnectionFactory.stopAecDump();
//        }
//
//        if (localPeer != null) {
//            localPeer.dispose();
//            localPeer = null;
//        }
//
//        if (audioSource != null) {
//            audioSource.dispose();
//            audioSource = null;
//        }
//
//        if (cameraVideoCapturer != null) {
//            try {
//                cameraVideoCapturer.stopCapture();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            cameraVideoCapturer.dispose();
//            cameraVideoCapturer = null;
//        }
//
//        if (videoSource != null) {
//            videoSource.dispose();
//            videoSource = null;
//        }
//
//        if (peerConnectionFactory != null) {
//            peerConnectionFactory.dispose();
//            peerConnectionFactory = null;
//        }
//
//        PeerConnectionFactory.stopInternalTracingCapture();
//        PeerConnectionFactory.shutdownInternalTracer();
//
//        handler.removeCallbacksAndMessages(null);
//        handler = null;
//
//        if(localVideoView != null){
//            localVideoView.clearImage();
//            localVideoView.release();
//        }
//
//        if(remoteVideoView != null){
//            remoteVideoView.clearImage();
//            remoteVideoView.release();
//        }
//
//        if (surfaceTextureHelper != null) {
//            surfaceTextureHelper.dispose();
//            surfaceTextureHelper = null;
//        }
//
//        ///////////////////////////////
//
//        super.onDestroy();
//
//        if (surfaceTextureHelper != null) {
//            surfaceTextureHelper.dispose();
//            surfaceTextureHelper = null;
//        }
//    }
//}
