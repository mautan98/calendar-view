package com.namviet.vtvtravel.response.travelnews;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class DetailTravelNewsResponse extends BaseResponse implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  implements Serializable {
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
        private NearBy nearBy;
        private String short_description;

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

        public class NearBy implements Serializable{
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
    }
}
