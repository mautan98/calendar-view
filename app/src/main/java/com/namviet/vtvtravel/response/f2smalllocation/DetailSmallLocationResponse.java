package com.namviet.vtvtravel.response.f2smalllocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class DetailSmallLocationResponse extends BaseResponse {
    private boolean canReload;

    public boolean isCanReload() {
        return canReload;
    }

    public void setCanReload(boolean canReload) {
        this.canReload = canReload;
    }

    private String detailLink;

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public  class Data {
        @Expose
        @SerializedName("discount")
        private String discount;
        @Expose
        @SerializedName("link_share")
        private String link_share;
        @Expose
        @SerializedName("banner_url")
        private String banner_url;
        @Expose
        @SerializedName("content_type")
        private String content_type;
        @Expose
        @SerializedName("region_id")
        private String region_id;
        @Expose
        @SerializedName("id")
        private String id;

        @Expose
        @SerializedName("tabs")
        private List<Tab> tabs;

        private boolean isLiked;
        private String likeCount;

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

        public List<Tab> getTabs() {
            return tabs;
        }

        public void setTabs(List<Tab> tabs) {
            this.tabs = tabs;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getLink_share() {
            return link_share;
        }

        public void setLink_share(String link_share) {
            this.link_share = link_share;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public class Tab {
            @Expose
            @SerializedName("type_open_color")
            private String typeOpenColor;


            private boolean isShow;

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }


            private String distance_text;

            private boolean has_location;
            @Expose
            @SerializedName("region_name")
            private String region_name;
            @Expose
            @SerializedName("evaluate")
            private String evaluate;
            @Expose
            @SerializedName("evaluate_text")
            private String evaluate_text;

            @Expose
            @SerializedName("url_images")
            private List<String> itemsGallery;
            @Expose
            @SerializedName("items")
            private List<Travel> items;
            @Expose
            @SerializedName("comment_count")
            private String comment_count;
            @Expose
            @SerializedName("view_count")
            private String view_count;
            @Expose
            @SerializedName("description")
            private String description;
            @Expose
            @SerializedName("footer")
            private Footer footer;
            @Expose
            @SerializedName("address")
            private String address;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("title")
            private String title;
            @Expose
            @SerializedName("code")
            private String code;
            @Expose
            @SerializedName("long")
            private String lng;
            @Expose
            @SerializedName("lat")
            private String lat;
            @Expose
            @SerializedName("tel")
            private String tel;
            @Expose
            @SerializedName("price_to")
            private String price_to;
            @Expose
            @SerializedName("price_from")
            private String price_from;
            @Expose
            @SerializedName("range_time")
            private String range_time;
            @Expose
            @SerializedName("open_week")
            private String open_week;
            @Expose
            @SerializedName("type_open")
            private String type_open;
            @Expose
            @SerializedName("distance")
            private String distance;
            @Expose
            @SerializedName("short_description")
            private String short_description;
            @Expose
            @SerializedName("website")
            private String website;

            @Expose
            @SerializedName("count_1")
            private String count_1;
            @Expose
            @SerializedName("count_2")
            private String count_2;
            @Expose
            @SerializedName("count_3")
            private String count_3;
            @Expose
            @SerializedName("count_4")
            private String count_4;
            @Expose
            @SerializedName("count_5")
            private String count_5;

            private String category_tree_code;
            private String category_tree_name;


            public String getCategory_tree_code() {
                return category_tree_code;
            }

            public String getCategory_tree_name() {
                return category_tree_name;
            }


            public String getTypeOpenColor() {
                return typeOpenColor;
            }

            public void setTypeOpenColor(String typeOpenColor) {
                this.typeOpenColor = typeOpenColor;
            }

            public String getDistance_text() {
                return distance_text;
            }

            public void setDistance_text(String distance_text) {
                this.distance_text = distance_text;
            }

            public boolean isHas_location() {
                return has_location;
            }

            public void setHas_location(boolean has_location) {
                this.has_location = has_location;
            }

            public String getCount_1() {
                return count_1;
            }

            public void setCount_1(String count_1) {
                this.count_1 = count_1;
            }

            public String getCount_2() {
                return count_2;
            }

            public void setCount_2(String count_2) {
                this.count_2 = count_2;
            }

            public String getCount_3() {
                return count_3;
            }

            public void setCount_3(String count_3) {
                this.count_3 = count_3;
            }

            public String getCount_4() {
                return count_4;
            }

            public void setCount_4(String count_4) {
                this.count_4 = count_4;
            }

            public String getCount_5() {
                return count_5;
            }

            public void setCount_5(String count_5) {
                this.count_5 = count_5;
            }

            public class Footer {
                @Expose
                @SerializedName("url_api")
                private String url_api;
                @Expose
                @SerializedName("description")
                private String description;
                @Expose
                @SerializedName("description_wap")
                private String description_wap;
                @Expose
                @SerializedName("url")
                private String url;

                public String getUrl_api() {
                    return url_api;
                }

                public void setUrl_api(String url_api) {
                    this.url_api = url_api;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getDescription_wap() {
                    return description_wap;
                }

                public void setDescription_wap(String description_wap) {
                    this.description_wap = description_wap;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public String getRegion_name() {
                return region_name;
            }

            public String getEvaluate() {
                return evaluate;
            }

            public String getEvaluate_text() {
                return evaluate_text;
            }

            public String getShort_description() {
                return short_description;
            }

            public void setShort_description(String short_description) {
                this.short_description = short_description;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }

            public List<Travel> getItems() {
                return items;
            }

            public void setItems(List<Travel> items) {
                this.items = items;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<String> getItemsGallery() {
                return itemsGallery;
            }

            public void setItemsGallery(List<String> itemsGallery) {
                this.itemsGallery = itemsGallery;
            }

            public String getComment_count() {
                return comment_count;
            }

            public void setComment_count(String comment_count) {
                this.comment_count = comment_count;
            }

            public String getView_count() {
                return view_count;
            }

            public void setView_count(String view_count) {
                this.view_count = view_count;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Footer getFooter() {
                return footer;
            }

            public void setFooter(Footer footer) {
                this.footer = footer;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getPrice_to() {
                return price_to;
            }

            public void setPrice_to(String price_to) {
                this.price_to = price_to;
            }

            public String getPrice_from() {
                return price_from;
            }

            public void setPrice_from(String price_from) {
                this.price_from = price_from;
            }

            public String getRange_time() {
                return range_time;
            }

            public void setRange_time(String range_time) {
                this.range_time = range_time;
            }

            public String getOpen_week() {
                return open_week;
            }

            public void setOpen_week(String open_week) {
                this.open_week = open_week;
            }

            public String getType_open() {
                return type_open;
            }

            public void setType_open(String type_open) {
                this.type_open = type_open;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
