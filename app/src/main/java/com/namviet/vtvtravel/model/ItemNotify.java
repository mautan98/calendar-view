package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemNotify implements Parcelable {
    //    {
//        "id": "5587e87e887b66fd754a96e1",
//            "type": "TEXT",
//            "title": "Dự báo thời tiết",
//            "content": "Thời tiết Hà Nội đang ấm dần lên",
//            "logo_url": "http://static.travel.onex.vn/data_files/events_files/image/201506/22/py1_hvoyy.jpg",
//            "created": 1434970238,
//            "linked": false,
//            "content_type": "places",
//            "content_link": "",
//            "read": true,
//            "view_count": 1,
//            "view_link": "http://api1.travel.onex.vn/notifications/view/5587e87e887b66fd754a96e1"
//    }
    private String id;
    private String type;
    private String title;
    private String content;
    private String logo_url;
    private long created;
    private boolean linked;
    private String content_type;
    private String content_link;
    private boolean read;
    private int view_count;
    private String view_link;
    private String content_id;


    protected ItemNotify(Parcel in) {
        id = in.readString();
        type = in.readString();
        title = in.readString();
        content = in.readString();
        logo_url = in.readString();
        created = in.readLong();
        linked = in.readByte() != 0;
        content_type = in.readString();
        content_link = in.readString();
        read = in.readByte() != 0;
        view_count = in.readInt();
        view_link = in.readString();
        content_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(logo_url);
        dest.writeLong(created);
        dest.writeByte((byte) (linked ? 1 : 0));
        dest.writeString(content_type);
        dest.writeString(content_link);
        dest.writeByte((byte) (read ? 1 : 0));
        dest.writeInt(view_count);
        dest.writeString(view_link);
        dest.writeString(content_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemNotify> CREATOR = new Creator<ItemNotify>() {
        @Override
        public ItemNotify createFromParcel(Parcel in) {
            return new ItemNotify(in);
        }

        @Override
        public ItemNotify[] newArray(int size) {
            return new ItemNotify[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getView_link() {
        return view_link;
    }

    public void setView_link(String view_link) {
        this.view_link = view_link;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    @Override
    public String toString() {
        return "ItemNotify{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", created=" + created +
                ", linked=" + linked +
                ", content_type='" + content_type + '\'' +
                ", content_link='" + content_link + '\'' +
                ", read=" + read +
                ", view_count=" + view_count +
                ", view_link='" + view_link + '\'' +
                '}';
    }
}
