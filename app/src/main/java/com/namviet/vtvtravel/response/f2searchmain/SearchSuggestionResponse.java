package com.namviet.vtvtravel.response.f2searchmain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.ArrayList;

public class SearchSuggestionResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private ArrayList<Item> items;

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }

        public  class Item {

            @Expose
            @SerializedName("icon")
            private String icon;
            @Expose
            @SerializedName("type")
            private String type;
            @Expose
            @SerializedName("title")
            private String title;
            @Expose
            @SerializedName("parent_name")
            private String parentName;
            @Expose
            @SerializedName("category_code")
            private String categoryCode;

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
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

            public String getCategoryCode() {
                return categoryCode;
            }

            public void setCategoryCode(String categoryCode) {
                this.categoryCode = categoryCode;
            }
        }
    }
}
