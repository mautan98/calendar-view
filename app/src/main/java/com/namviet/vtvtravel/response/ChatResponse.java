package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.chat.ChatAnswer;
import com.namviet.vtvtravel.model.chat.ChatData;

public class ChatResponse {
    private ChatData data;

    public ChatData getData() {
        return data;
    }

    public void setData(ChatData data) {
        this.data = data;
    }
}
