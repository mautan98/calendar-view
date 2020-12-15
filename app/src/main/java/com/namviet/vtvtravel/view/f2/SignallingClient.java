package com.namviet.vtvtravel.view.f2;

//import android.annotation.SuppressLint;
//import android.util.Log;
//
//import com.github.nkzawa.emitter.Emitter;
//import com.github.nkzawa.socketio.client.Ack;
//import com.github.nkzawa.socketio.client.IO;
//import com.github.nkzawa.socketio.client.Socket;
//import com.google.gson.Gson;
//import com.namviet.vtvtravel.app.MyApplication;
//import com.namviet.vtvtravel.model.call.ObjectForSignaling;
//import com.namviet.vtvtravel.socket.SocketManager;
//
//import org.json.JSONObject;
//import org.webrtc.IceCandidate;
//import org.webrtc.SessionDescription;
//
//import java.net.URISyntaxException;
//import java.security.cert.X509Certificate;
//
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//public class SignallingClient {
//    public static int OFFER_OR_ANSWER_SIGNALLING_TYPE = 1;
//    public static int CANDIDATE_SIGNALLING_TYPE = 3;
//    public static int HANG_UP_SIGNALLING_TYPE = 4;
//    public static String TAG = "Debug SignallingClient";
//    public static final String KEY = "exchange_signaling_message";
//
//
//    public static boolean isCaller = true;
//    public static final int FROM_CALL = 0;
//    public static final int FROM_ANSWER = 1;
//
//    private static SignallingClient instance;
//    private String roomName = null;
//    private Socket socket;
//    boolean isChannelReady = false;
//    public boolean isInitiator = false;
//    public boolean isStarted = false;
//    private SignalingInterface callback;
//    private String roomId;
//    private int fromWhere = 0;
//    private boolean isVideoCall;
//
//    //This piece of code should not go into production!!
//    //This will help in cases where the node server is running in non-https server and you want to ignore the warnings
//    @SuppressLint("TrustAllX509TrustManager")
//    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
//        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//            return new java.security.cert.X509Certificate[]{};
//        }
//
//        public void checkClientTrusted(X509Certificate[] chain,
//                                       String authType) {
//        }
//
//        public void checkServerTrusted(X509Certificate[] chain,
//                                       String authType) {
//        }
//    }};
//
//    public static SignallingClient getInstance() {
//        if (instance == null) {
//            instance = new SignallingClient();
//        }
//        if (instance.roomName == null) {
//            //set the room name here
//            instance.roomName = "vivek17";
//        }
//        return instance;
//    }
//
//    public void init(SignalingInterface signalingInterface, String roomId, int fromWhere, boolean isVideoCall) {
//        this.callback = signalingInterface;
//        this.roomId = roomId;
//        this.fromWhere = fromWhere;
//        this.isVideoCall = isVideoCall;
//        try {
//
//            IO.Options opts = new IO.Options();
//            opts.forceNew = true;
//            opts.path = "/chat-socket";
//            opts.query = "auth_token=" + MyApplication.getInstance().getAccount().getToken();
//            socket = IO.socket(SocketManager.URL_CALL, opts);
//            socket.connect();
//
//
//
////            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
////                @Override
////                public void call(Object... args) {
////                    if(fromWhere == FROM_ANSWER){
////                        isInitiator = true;
////                        callback.onTryToStart();
////                    }
////                }
////            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
////                @Override
////                public void call(Object... args) {
////                    Log.e("", "");
////                }
////            });
//
//            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT", "hihi");
//                }
//            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            }).on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            }).on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            }).on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("EVENT_CONNECT_ERROR", "hihi");
//                }
//            });
//
//            if (fromWhere == FROM_CALL) {
//                emitWakeUpViaNotification(this.roomId);
//            }
//
//            socket.on(KEY, args -> {
//                try {
//                    ObjectForSignaling objectForSignaling  = new Gson().fromJson(String.valueOf(args[0]), ObjectForSignaling.class);
//                    int type = Integer.parseInt(objectForSignaling.getType());
//                    if (type == OFFER_OR_ANSWER_SIGNALLING_TYPE) {
//                        JSONObject data = new JSONObject();
//                        data.put("sdp", objectForSignaling.getPayload().getSignalData().getSdp());
//                        String type1 = objectForSignaling.getPayload().getSignalData().getType();
//                        if (type1.equalsIgnoreCase("offer")) {
//                            if (objectForSignaling.getPayload().getSenderInfo().getId() != MyApplication.getInstance().getAccount().getId()) {
//                                callback.onOfferReceived(data);
//                                Log.e(TAG, "Nhận được offer");
//
//                            }
//                        } else if (type1.equalsIgnoreCase("answer") && isStarted) {
//                            data.put("type", "answer");
//                            if (objectForSignaling.getPayload().getSenderInfo().getId() != MyApplication.getInstance().getAccount().getId()) {
//                                callback.onAnswerReceived(data);
//                                Log.e(TAG, "Nhận được answer");
//                            }
//                        } else if (type1.equalsIgnoreCase("candidate") && isStarted) {
//                            if (objectForSignaling.getPayload().getSenderInfo().getId() != MyApplication.getInstance().getAccount().getId()) {
//                                callback.onIceCandidateReceived(data);
//                            }
//                        }
//                    } else if (type == CANDIDATE_SIGNALLING_TYPE) {
//                        JSONObject data = new JSONObject();
//                        data.put("sdpMid", objectForSignaling.getPayload().getSignalData().getId());
//                        data.put("sdpMLineIndex", objectForSignaling.getPayload().getSignalData().getLabel());
//                        data.put("sdp", objectForSignaling.getPayload().getSignalData().getSdp());
//                        if (isStarted) {
//                            if (objectForSignaling.getPayload().getSenderInfo().getId() != MyApplication.getInstance().getAccount().getId()) {
//                                callback.onIceCandidateReceived(data);
//                                Log.e(TAG, "Nhận được candidate");
//                            }
//                        }
//                    }else if(type == HANG_UP_SIGNALLING_TYPE){
//                        if (objectForSignaling.getPayload().getSenderInfo().getId()  != MyApplication.getInstance().getAccount().getId()) {
//                            callback.onHangup();
//                        }
//                    }
//
//                } catch (Exception e) {
//                    Log.e("", "");
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static String TYPE = "type";
//    public static String ROOM_ID = "room_id";
//    public static String ROOMID = "roomId";
//    public static String IS_HAS_VIDEO = "isHasVideo";
//    public static String FULL_NAME = "fullName";
//    public static String ID = "id";
//    public static String SENDER_INFO = "senderInfo";
//    public static String PAYLOAD = "payload";
//
//    private void emitWakeUpViaNotification(String roomId) {
//        try {
//
//            JSONObject object = new JSONObject();
//            object.put(TYPE, 0);
//            object.put(ROOM_ID, roomId);
//
//
//            JSONObject payload = new JSONObject();
//            payload.put(ROOMID, roomId);
//            payload.put(IS_HAS_VIDEO, this.isVideoCall);
//
//
//            JSONObject senderInfo = new JSONObject();
//            senderInfo.put(FULL_NAME, MyApplication.getInstance().getAccount().getFullname());
//            senderInfo.put(ID, MyApplication.getInstance().getAccount().getId());
//
//            payload.put(SENDER_INFO, senderInfo);
//
//            object.put(PAYLOAD, payload);
//
//            socket.emit(KEY, object, new Ack() {
//                @Override
//                public void call(Object... args) {
//                    Log.e("", "");
//                }
//            });
//        } catch (Exception e) {
//            Log.e("", "");
//        }
//    }
//
//
//    public void emitMessage(SessionDescription message) {
//
//    }
//
//
//    //thanh add
//    public void emitMessageOfMine(SessionDescription message, String roomId) {
//        try {
//
//            JSONObject object = new JSONObject();
//            object.put(TYPE, 1);
//            object.put(ROOM_ID, roomId);
//
//
//            JSONObject payload = new JSONObject();
//            payload.put(ROOMID, roomId);
//
//            JSONObject senderInfo = new JSONObject();
//            senderInfo.put(FULL_NAME, MyApplication.getInstance().getAccount().getFullname());
//            senderInfo.put(ID, MyApplication.getInstance().getAccount().getId());
//
//            JSONObject signalData = new JSONObject();
//            signalData.put("sdp", message.description);
//            signalData.put("type", message.type.canonicalForm());
//
//            payload.put(SENDER_INFO, senderInfo);
//            payload.put("signalData", signalData);
//
//            object.put(PAYLOAD, payload);
//
//            socket.emit(KEY, object);
//            Log.e("SignalingResponse", "emitMessageOfMine");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void emitIceCandidateOfMine(IceCandidate iceCandidate, String roomId) {
//        try {
//
//            JSONObject object = new JSONObject();
//            object.put(TYPE, 3);
//            object.put(ROOM_ID, roomId);
//
//
//            JSONObject payload = new JSONObject();
//            payload.put(ROOMID, roomId);
//
//            JSONObject senderInfo = new JSONObject();
//            senderInfo.put(FULL_NAME, MyApplication.getInstance().getAccount().getFullname());
//            senderInfo.put(ID, MyApplication.getInstance().getAccount().getId());
//
//
//            JSONObject signalData = new JSONObject();
////            signalData.put("type", "sdp");
//            signalData.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
//            signalData.put("sdpMid", iceCandidate.sdpMid);
//            signalData.put("sdp", iceCandidate.sdp);
//
//
//
//            payload.put(SENDER_INFO, senderInfo);
//            payload.put("signalData", signalData);
//
//            object.put(PAYLOAD, payload);
//
//            socket.emit(KEY, object);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void emitHangup(String roomId){
//        try {
//
//            JSONObject obj = new JSONObject();
//            obj.put(TYPE, 4);
//            obj.put(ROOM_ID, roomId);
//
//            JSONObject payload = new JSONObject();
//            payload.put(ROOMID, roomId);
//
//            JSONObject senderInfo = new JSONObject();
//            senderInfo.put(FULL_NAME, MyApplication.getInstance().getAccount().getFullname());
//            senderInfo.put(ID, MyApplication.getInstance().getAccount().getId());
//
//            payload.put(SENDER_INFO, senderInfo);
//            obj.put(PAYLOAD, payload);
//            socket.emit(KEY, obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void close() {
//
////        socket.emit("bye", roomName);
//        if(socket != null) {
//            socket.disconnect();
//        }
//
//        if(socket != null) {
//            socket.close();
//        }
//
//        instance = null;
//        roomName = null;
//        socket = null;
//        isChannelReady = false;
//        isInitiator = false;
//        isStarted = false;
//        callback = null;
//        roomId = null;
//        fromWhere = 0;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }
//
//    interface SignalingInterface {
//        void onRemoteHangUp(String msg);
//
//        void onOfferReceived(JSONObject data);
//
//        void onAnswerReceived(JSONObject data);
//
//        void onIceCandidateReceived(JSONObject data);
//
//        void onTryToStart();
//
//        void onWakeUp(JSONObject data);
//
//        void onHangup();
//    }
//}
