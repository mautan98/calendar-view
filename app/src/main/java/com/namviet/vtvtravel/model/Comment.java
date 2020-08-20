package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Comment implements Parcelable {
    private int id;
    private int userId;
    private String contentId;
    private String content;
    private String created;
    private Account user;
    private List<Comment> children;

    protected Comment(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        contentId = in.readString();
        content = in.readString();
        created = in.readString();
        children = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeString(contentId);
        parcel.writeString(content);
        parcel.writeString(created);
        parcel.writeTypedList(children);
    }
}
