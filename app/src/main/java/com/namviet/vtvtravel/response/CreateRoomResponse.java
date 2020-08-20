package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.chat.Room;

public class CreateRoomResponse {
    private Room data;

    public Room getData() {
        return data;
    }

    public void setData(Room data) {
        this.data = data;
    }
}
