package com.namviet.vtvtravel.socket;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.namviet.vtvtravel.api.ParamSocket;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.config.Constants;
import com.namviet.vtvtravel.help.SocketOn;
import com.namviet.vtvtravel.model.chat.ChatData;
import com.namviet.vtvtravel.model.f2event.OnSocketSendSurvey;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class SocketManager {

    public static final String URL_CALL = "http://103.21.148.54:8079";

    private static final String URL = "http://103.21.148.54:8079";
//    private static final String URL = "https://chat.vtvtravel.vn";

    public static Socket mSocket;

    private static final String SEND_MESSAGE = "send_message";

    private static final String RECEIVE_MESSAGE = "receive_message";
    private static final String NOT_REPLY_MESSAGE = "not_reply_message";
    private static final String ADMIN_SEND_SURVEY = "admin_send_survey";


    /* Create socket + call connect */
    public static void initSocket(String token) {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.path = "/chat-socket";
            opts.query = "auth_token=" + token;
            mSocket = IO.socket(URL, opts);
            mSocket.connect();


            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT", "hihi");
                }
            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT_ERROR", "hihi");
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /* EMIT A MESSAGE */
    public static void emitMessage(String roomId, String message, String clientMessageID) {
        JSONObject jsonObject = ParamSocket.emitMessage(roomId, message, clientMessageID);
        mSocket.emit(SEND_MESSAGE, jsonObject, new Ack() {
            @Override
            public void call(Object... args) {
                Log.e("", "");
            }
        });
    }

    /* ON  RECEIVE A MESSAGE */
    public static void onMessage() {
        // ack from client to server
        mSocket.on(RECEIVE_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    SocketOn socketOn = new SocketOn(0, args);
                    ChatData newMessage = newMessage(socketOn.getI(), socketOn.getArgs());
                    MyApplication.getInstance().getmChatDatas().add(newMessage);
                    EventBus.getDefault().post(new SocketOn());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                handleChat(newMessage);
            }
        });
    }

    /* ON  RECEIVE A MESSAGE */
    public static void onNotReplyMessage() {
        // ack from client to server
        mSocket.on(NOT_REPLY_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                EventBus.getDefault().post(new SocketOn(1, args));
            }
        });
    }

    /* ON  RECEIVE A MESSAGE */
    public static void onAdminSendSurvey() {
        // ack from client to server
        mSocket.on(ADMIN_SEND_SURVEY, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                EventBus.getDefault().post(new OnSocketSendSurvey());
            }
        });
    }

    /* NEW MESSAGE */
    private static ChatData newMessage(int type, Object... args) {
        ChatData chatData = new Gson().fromJson(args[0].toString(), ChatData.class);
        return updateMessageByType(chatData, type);
    }

    /*Update massage by type*/
    private static ChatData updateMessageByType(ChatData message, int type) {
        // 0 is on receive message
        // 1 is typing message
        String accountIdUser = MyApplication.getInstance().getRoom().getMemberId();
        switch (type) {
            case 0:
                if (isSameUser(accountIdUser, message)) {
                    message.setOwner(true);
                    message.setType(Constants.TypeChat.USER_SOCKET);
                    message.setCurrent_time(getCurrentTime());
                } else {
                    message.setOwner(false);
                    message.setType(Constants.TypeChat.ADMIN);
                    message.setCurrent_time(getCurrentTime());
                }
                break;
            case 1:
                message.setOwner(false);
                message.setType(Constants.TypeChat.NOT_REPLY);

                ChatData.Sender sender = new ChatData.Sender();
                sender.setFull_name("Tổng đài viên - VTVTravel");
                message.setSender(new ChatData.Sender());
                message.setCurrent_time(getCurrentTime());
                break;
            default:
                break;
        }
        return message;

    }

    /*CHECK SAME USER ID*/
    private static boolean isSameUser(String idCurrentUser, ChatData message) {
        try {
            return idCurrentUser.equals(message.getSender().getAdminId());
        } catch (Exception e) {
            return idCurrentUser.equals(message.getRoomId());
        }
    }

    private static String getCurrentTime() {
        Calendar rightNow = Calendar.getInstance();

        int currentHourIn12Format = rightNow.get(Calendar.HOUR);
        int currentMinuteIn12Format = rightNow.get(Calendar.MINUTE);

        String hour;
        String minute;
        if (currentHourIn12Format < 10) {
            hour = "0" + currentHourIn12Format;
        } else {
            hour = String.valueOf(currentHourIn12Format);
        }

        if (currentMinuteIn12Format < 10) {
            minute = "0" + currentMinuteIn12Format;
        } else {
            minute = String.valueOf(currentMinuteIn12Format);
        }
        int currentTypeIn12Format = rightNow.get(Calendar.AM_PM);
        String time;
        if (currentTypeIn12Format == Calendar.AM) {
            time = hour + ":" + minute + " AM";
        } else {
            time = hour + ":" + minute + " PM";
        }
        return time;
    }
}
