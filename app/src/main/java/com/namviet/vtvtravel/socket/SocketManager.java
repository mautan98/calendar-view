package com.namviet.vtvtravel.socket;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.namviet.vtvtravel.api.ParamSocket;
import com.namviet.vtvtravel.help.SocketOn;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class SocketManager {

    private static final String URL = "http://103.21.148.54:8079";
    public static Socket mSocket;

    private static final String SEND_MESSAGE = "send_message";

    private static final String RECEIVE_MESSAGE = "receive_message";
    private static final String NOT_REPLY_MESSAGE = "not_reply_message";


    /* Create socket + call connect */
    public static void initSocket(String token) {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.path = "/chat-socket";
            opts.query = "auth_token=" + token;
            mSocket = IO.socket(URL, opts);
            mSocket.connect();
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
                EventBus.getDefault().post(new SocketOn(0, args));
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

}
