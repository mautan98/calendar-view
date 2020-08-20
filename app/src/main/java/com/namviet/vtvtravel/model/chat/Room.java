package com.namviet.vtvtravel.model.chat;

public class Room {
    private String tokenMemberId;
    private String memberId;
    private ContentRoom room;

    public ContentRoom getRoom() {
        return room;
    }

    public void setRoom(ContentRoom room) {
        this.room = room;
    }

    public String getTokenMemberId() {
        return tokenMemberId;
    }

    public void setTokenMemberId(String tokenMemberId) {
        this.tokenMemberId = tokenMemberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public class ContentRoom {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
