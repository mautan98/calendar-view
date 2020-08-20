package com.namviet.vtvtravel.api;

import org.json.JSONException;
import org.json.JSONObject;

public class ParamSocket {

    public static JSONObject emitMessage(String roomId, String message, String clientMessageId) {
        JSONObject map = new JSONObject();
        try {
            map.put(WSConfig.KeySocket.ROOM_ID, roomId);
            map.put(WSConfig.KeySocket.CONTENT, message);
            map.put(WSConfig.KeySocket.CLIENT_MESSAGE_ID, clientMessageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
