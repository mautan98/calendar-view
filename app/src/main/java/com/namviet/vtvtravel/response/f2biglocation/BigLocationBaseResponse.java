package com.namviet.vtvtravel.response.f2biglocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class BigLocationBaseResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        @Expose
        @SerializedName("region")
        private Region region;

        @Expose
        @SerializedName("items")
        private List<Item> listDataMain;

        public List<Item> getListDataMain() {
            return listDataMain;
        }

        public void setListDataMain(List<Item> listDataMain) {
            this.listDataMain = listDataMain;
        }

        public Region getRegion() {
            return region;
        }

        public void setRegion(Region region) {
            this.region = region;
        }

        public class Region {

            @Expose
            @SerializedName("items")
            private List<Items> items;
            @Expose
            @SerializedName("created")
            private String created;
            @Expose
            @SerializedName("view_count")
            private String view_count;
            @Expose
            @SerializedName("thumb_url")
            private List<String> thumb_url;
            @Expose
            @SerializedName("banner_url")
            private String banner_url;
            @Expose
            @SerializedName("area_code")
            private String area_code;
            @Expose
            @SerializedName("address")
            private String address;
            @Expose
            @SerializedName("phone_local_region")
            private String phone_local_region;
            @Expose
            @SerializedName("description")
            private String description;
            @Expose
            @SerializedName("zip_code")
            private String zip_code;
            @Expose
            @SerializedName("loc")
            private Loc loc;
            @Expose
            @SerializedName("country_code")
            private String country_code;
            @Expose
            @SerializedName("code_name")
            private String code_name;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("id")
            private String id;

            public List<Items> getItems() {
                return items;
            }

            private boolean isShow;

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }


            public void setItems(List<Items> items) {
                this.items = items;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getView_count() {
                return view_count;
            }

            public void setView_count(String view_count) {
                this.view_count = view_count;
            }

            public List<String> getThumb_url() {
                return thumb_url;
            }

            public void setThumb_url(List<String> thumb_url) {
                this.thumb_url = thumb_url;
            }

            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }

            public String getArea_code() {
                return area_code;
            }

            public void setArea_code(String area_code) {
                this.area_code = area_code;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone_local_region() {
                return phone_local_region;
            }

            public void setPhone_local_region(String phone_local_region) {
                this.phone_local_region = phone_local_region;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getZip_code() {
                return zip_code;
            }

            public void setZip_code(String zip_code) {
                this.zip_code = zip_code;
            }

            public Loc getLoc() {
                return loc;
            }

            public void setLoc(Loc loc) {
                this.loc = loc;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public String getCode_name() {
                return code_name;
            }

            public void setCode_name(String code_name) {
                this.code_name = code_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public  class Items {
                @Expose
                @SerializedName("content_link")
                private String content_link;
                @Expose
                @SerializedName("banner_url")
                private String banner_url;
                @Expose
                @SerializedName("weight")
                private String weight;
                @Expose
                @SerializedName("html_icon")
                private String html_icon;
                @Expose
                @SerializedName("link")
                private String link;
                @Expose
                @SerializedName("icon_url")
                private String icon_url;
                @Expose
                @SerializedName("name")
                private String name;
                @Expose
                @SerializedName("code")
                private String code;
                @Expose
                @SerializedName("id")
                private String id;


                public String getContent_link() {
                    return content_link;
                }

                public void setContent_link(String content_link) {
                    this.content_link = content_link;
                }

                public String getBanner_url() {
                    return banner_url;
                }

                public void setBanner_url(String banner_url) {
                    this.banner_url = banner_url;
                }

                public String getWeight() {
                    return weight;
                }

                public void setWeight(String weight) {
                    this.weight = weight;
                }

                public String getHtml_icon() {
                    return html_icon;
                }

                public void setHtml_icon(String html_icon) {
                    this.html_icon = html_icon;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getIcon_url() {
                    return icon_url;
                }

                public void setIcon_url(String icon_url) {
                    this.icon_url = icon_url;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public  class Loc {
                @Expose
                @SerializedName("coordinates")
                private List<String> coordinates;
                @Expose
                @SerializedName("type")
                private String type;

                public List<String> getCoordinates() {
                    return coordinates;
                }

                public void setCoordinates(List<String> coordinates) {
                    this.coordinates = coordinates;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public class Item {

            @Expose
            @SerializedName("banner_url")
            private String banner_url;
            @Expose
            @SerializedName("weight")
            private String weight;
            @Expose
            @SerializedName("html_icon")
            private String html_icon;
            @Expose
            @SerializedName("link")
            private String link;
            @Expose
            @SerializedName("icon_url")
            private String icon_url;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("code_type")
            private String code_type;
            @Expose
            @SerializedName("code")
            private String code;
            @Expose
            @SerializedName("id")
            private String id;

            @Expose
            @SerializedName("api_items")
            private String api_items;

            @Expose
            @SerializedName("items")
            private List<Travel> items;


            public String getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(String banner_url) {
                this.banner_url = banner_url;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getHtml_icon() {
                return html_icon;
            }

            public void setHtml_icon(String html_icon) {
                this.html_icon = html_icon;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode_type() {
                return code_type;
            }

            public void setCode_type(String code_type) {
                this.code_type = code_type;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
