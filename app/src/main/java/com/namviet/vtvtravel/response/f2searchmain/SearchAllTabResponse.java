package com.namviet.vtvtravel.response.f2searchmain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.ArrayList;

public class SearchAllTabResponse extends BaseResponse {
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
            @SerializedName("videos")
            private String videos;
            @Expose
            @SerializedName("news")
            private String news;
            @Expose
            @SerializedName("places")
            private String places;

            public String getVideos() {
                return videos;
            }

            public void setVideos(String videos) {
                this.videos = videos;
            }

            public String getNews() {
                return news;
            }

            public void setNews(String news) {
                this.news = news;
            }

            public String getPlaces() {
                return places;
            }

            public void setPlaces(String places) {
                this.places = places;
            }
        }
    }
}
