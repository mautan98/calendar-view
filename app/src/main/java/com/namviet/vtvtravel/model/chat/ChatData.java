package com.namviet.vtvtravel.model.chat;

import com.namviet.vtvtravel.response.f2chat.GetUserGuildResponse;
import com.namviet.vtvtravel.response.f2chat.PostUserGuildResponse;

import java.util.ArrayList;
import java.util.List;

public class ChatData {
    private String avatar_url;
    private float created;
    private String type;
    private String text;
    private float status;
    private String lang_code;
    private String current_time;
    List<ChatAnswer> answers = new ArrayList<ChatAnswer>();
    private List<String> text_sensitive = new ArrayList<>();

    private String roomId;
    private String content;
    private String messageId;
    private Sender sender;
    private boolean isOwner;

    private List<GetUserGuildResponse.Data.Item> itemGetUserGuild = new ArrayList<>();
    private List<PostUserGuildResponse.Data.Item> itemPostUserGuild = new ArrayList<>();


    public ChatData() {
    }

    public ChatData(String avatar_url, float created, String type, String text, float status, String lang_code) {
        this.avatar_url = avatar_url;
        this.created = created;
        this.type = type;
        this.text = text;
        this.status = status;
        this.lang_code = lang_code;
    }



    public ChatData(String type, String text, String current_time, String avatar_url) {
        this.type = type;
        this.text = text;
        this.current_time = current_time;
        this.avatar_url = avatar_url;
    }

    public ChatData(String type, String text, String current_time, String avatar_url, Sender sender) {
        this.type = type;
        this.text = text;
        this.current_time = current_time;
        this.avatar_url = avatar_url;
        this.sender = sender;
    }


    public ChatData(String mess, String currentTime, String image) {
        this.text = text;
        this.current_time = current_time;
        this.avatar_url = avatar_url;
    }

    public List<String> getText_sensitive() {
        return text_sensitive;
    }

    public void setText_sensitive(List<String> text_sensitive) {
        this.text_sensitive = text_sensitive;
    }

    public List<GetUserGuildResponse.Data.Item> getItemGetUserGuild() {
        return itemGetUserGuild;
    }

    public void setItemGetUserGuild(List<GetUserGuildResponse.Data.Item> itemGetUserGuild) {
        this.itemGetUserGuild = itemGetUserGuild;
    }

    public List<PostUserGuildResponse.Data.Item> getItemPostUserGuild() {
        return itemPostUserGuild;
    }

    public void setItemPostUserGuild(List<PostUserGuildResponse.Data.Item> itemPostUserGuild) {
        this.itemPostUserGuild = itemPostUserGuild;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public List<ChatAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ChatAnswer> answers) {
        this.answers = answers;
    }
// Getter Methods

    public String getAvatar_url() {
        return avatar_url;
    }

    public float getCreated() {
        return created;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public float getStatus() {
        return status;
    }

    public String getLang_code() {
        return lang_code;
    }

    // Setter Methods

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setCreated(float created) {
        this.created = created;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(float status) {
        this.status = status;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public static class Sender {
        private String adminId;
        private String username;
        private String avatarUrl;
        private String type;
        private String full_name;

        public Sender() {
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }
    }
}
