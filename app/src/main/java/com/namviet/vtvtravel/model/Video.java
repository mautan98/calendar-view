package com.namviet.vtvtravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.namviet.vtvtravel.response.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class Video implements Parcelable {


    private String id;
    private String content_type;
    private String name;
    private String short_description;
    private String logo_url;
    private int view_count;
    private long created;
    private String streaming_url;
    private String source_url;
    private String poster_url;
    private ArrayList<String> hash_tags;
    private CategoryItem category;
    private String detail_link;
    private List<Ads> ads_vasts;
    private String comment_count;
    private String link_share;
    private boolean isLiked;
    private String likeCount;
    private String category_tree_code;
    private String category_tree_name;

    public String getCategory_tree_code() {
        return category_tree_code;
    }

    public String getCategory_tree_name() {
        return category_tree_name;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public Video() {
    }

    public Video(String id, String detail_link) {
        this.id = id;
        this.detail_link = detail_link;
    }


    protected Video(Parcel in) {
        id = in.readString();
        content_type = in.readString();
        name = in.readString();
        short_description = in.readString();
        logo_url = in.readString();
        view_count = in.readInt();
        created = in.readLong();
        streaming_url = in.readString();
        source_url = in.readString();
        poster_url = in.readString();
        hash_tags = in.createStringArrayList();
        category = in.readParcelable(CategoryItem.class.getClassLoader());
        detail_link = in.readString();
        ads_vasts = in.createTypedArrayList(Ads.CREATOR);
        comment_count = in.readString();
        link_share = in.readString();
        category_tree_code = in.readString();
        category_tree_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content_type);
        dest.writeString(name);
        dest.writeString(short_description);
        dest.writeString(logo_url);
        dest.writeInt(view_count);
        dest.writeLong(created);
        dest.writeString(streaming_url);
        dest.writeString(source_url);
        dest.writeString(poster_url);
        dest.writeStringList(hash_tags);
        dest.writeParcelable(category, flags);
        dest.writeString(detail_link);
        dest.writeTypedList(ads_vasts);
        dest.writeString(comment_count);
        dest.writeString(link_share);
        dest.writeString(category_tree_code);
        dest.writeString(category_tree_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getStreaming_url() {
        return streaming_url;
    }

    public void setStreaming_url(String streaming_url) {
        this.streaming_url = streaming_url;
    }

    public ArrayList<String> getHash_tags() {
        return hash_tags;
    }

    public void setHash_tags(ArrayList<String> hash_tags) {
        this.hash_tags = hash_tags;
    }

    public CategoryItem getCategory() {
        return category;
    }

    public void setCategory(CategoryItem category) {
        this.category = category;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public List<Ads> getAds_vasts() {
        return ads_vasts;
    }

    public void setAds_vasts(List<Ads> ads_vasts) {
        this.ads_vasts = ads_vasts;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getLink_share() {
        return link_share;
    }

    public void setLink_share(String link_share) {
        this.link_share = link_share;
    }
}
