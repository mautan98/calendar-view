package com.namviet.vtvtravel.response.travelnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailTravelNewsResponse extends BaseResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {
        private String id;
        private String content_type;
        private String type;
        private String title;
        private String name;
        private String logo_url;
        private String description;
        private String view_count;
        private String created;
        private String author;
        private String time_ago;
        private String count_like;
        private String count_comment;
        private String link_share;
        @Expose
        @SerializedName("near_by")
        private NearBy nearBy;
        private String short_description;
        private boolean isLiked;
        private String likeCount;
        private Relation related_news;
        private String category_tree_code;
        private String category_tree_name;


        @Expose
        @SerializedName("near_by")
        private PlaceNearBy placeNearBy;

        @Expose
        @SerializedName("api_related_news")
        private PlaceNearBy relatedNews;

        @Expose
        @SerializedName("api_related_place")
        private PlaceNearBy relatedPlaces;

        public PlaceNearBy getRelatedNews() {
            return relatedNews;
        }

        public PlaceNearBy getRelatedPlaces() {
            return relatedPlaces;
        }

        public PlaceNearBy getPlaceNearBy() {
            return placeNearBy;
        }

        public String getCategory_tree_code() {
            return category_tree_code;
        }

        public String getCategory_tree_name() {
            return category_tree_name;
        }

        public Relation getRelations() {
            return related_news;
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

        public String getShort_description() {
            return short_description;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setTime_ago(String time_ago) {
            this.time_ago = time_ago;
        }

        public void setCount_like(String count_like) {
            this.count_like = count_like;
        }

        public void setCount_comment(String count_comment) {
            this.count_comment = count_comment;
        }

        public void setNearBy(NearBy nearBy) {
            this.nearBy = nearBy;
        }

        public class NearBy implements Serializable {
            private String title;
            private List<Travel> items;

            public String getTitle() {
                return title;
            }

            public List<Travel> getItems() {
                return items;
            }
        }

        public NearBy getNearBy() {
            return nearBy;
        }

        public String getId() {
            return id;
        }

        public String getContent_type() {
            return content_type;
        }

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getName() {
            return name;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public String getDescription() {
            return description;
        }

        public String getView_count() {
            return view_count;
        }

        public String getCreated() {
            return created;
        }

        public String getAuthor() {
            return author;
        }

        public String getTime_ago() {
            return time_ago;
        }

        public String getCount_like() {
            return count_like;
        }

        public String getCount_comment() {
            return count_comment;
        }

        public String getLink_share() {
            return link_share;
        }

        public void setLink_share(String link_share) {
            this.link_share = link_share;
        }


        public class Relation implements Serializable {

            private List<Travel> items;

            public List<Travel> getItems() {
                return items;
            }
        }

        public  class PlaceNearBy implements Serializable {
            @Expose
            @SerializedName("api_link")
            private String api_link;
            @Expose
            @SerializedName("title")
            private String title;
            @Expose
            @SerializedName("tab")
            private ArrayList<Tab> tabs;

            public String getApi_link() {
                return api_link;
            }

            public void setApi_link(String api_link) {
                this.api_link = api_link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public ArrayList<Tab> getTabs() {
                return tabs;
            }

            public void setTabs(ArrayList<Tab> tabs) {
                this.tabs = tabs;
            }

            public class Tab implements Serializable {
                @Expose
                @SerializedName("content_link")
                private String content_link;
                @Expose
                @SerializedName("code")
                private String code;
                @Expose
                @SerializedName("name")
                private String name;

                public String getContent_link() {
                    return content_link;
                }

                public void setContent_link(String content_link) {
                    this.content_link = content_link;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
