package com.daimajia.slider.library;

import android.os.Parcel;
import android.os.Parcelable;

public class Travel implements Parcelable {
    private String id;
    private String code;
    private String name;
    private String logo_url;
    private String banner_url;
    private String photo_url;
    private Long created;
    private Integer view_count;
    private String address;
    private String detail_link;
    private String type;
    private String content_type;
    private String region_id;
    private String author;
    private double lng;
    private double lat;
    private int distance;
    private Collection collection;
    private float standard_rate;
    private String detail_link_id;
    private String url_icon;

    public String getUrl_icon() {
        return url_icon;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }

    public String getDetail_link_id() {
        return detail_link_id;
    }

    public void setDetail_link_id(String detail_link_id) {
        this.detail_link_id = detail_link_id;
    }

    public Travel(String id, String name, String logo_url) {
        this.id = id;
        this.name = name;
        this.logo_url = logo_url;
    }

    public Travel() {
    }


    protected Travel(Parcel in) {
        id = in.readString();
        code = in.readString();
        name = in.readString();
        logo_url = in.readString();
        banner_url = in.readString();
        photo_url = in.readString();
        if (in.readByte() == 0) {
            created = null;
        } else {
            created = in.readLong();
        }
        if (in.readByte() == 0) {
            view_count = null;
        } else {
            view_count = in.readInt();
        }
        address = in.readString();
        detail_link = in.readString();
        type = in.readString();
        content_type = in.readString();
        region_id = in.readString();
        author = in.readString();
        lng = in.readDouble();
        lat = in.readDouble();
        distance = in.readInt();
        collection = in.readParcelable(Collection.class.getClassLoader());
        standard_rate = in.readFloat();
        url_icon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(logo_url);
        dest.writeString(banner_url);
        dest.writeString(photo_url);
        if (created == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(created);
        }
        if (view_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(view_count);
        }
        dest.writeString(address);
        dest.writeString(detail_link);
        dest.writeString(type);
        dest.writeString(content_type);
        dest.writeString(region_id);
        dest.writeString(author);
        dest.writeDouble(lng);
        dest.writeDouble(lat);
        dest.writeInt(distance);
        dest.writeParcelable(collection, flags);
        dest.writeFloat(standard_rate);
        dest.writeString(url_icon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Travel> CREATOR = new Creator<Travel>() {
        @Override
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }

        @Override
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double log) {
        this.lng = log;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public float getStandard_rate() {
        return standard_rate;
    }

    public void setStandard_rate(float standard_rate) {
        this.standard_rate = standard_rate;
    }
}
