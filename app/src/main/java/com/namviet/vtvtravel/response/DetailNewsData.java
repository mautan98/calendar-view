package com.namviet.vtvtravel.response;

import com.daimajia.slider.library.Travel;
import com.namviet.vtvtravel.model.News;

import java.util.ArrayList;
import java.util.List;

public class DetailNewsData {
    private String id;
    private String content_type;
    private String title;
    private String name;
    private String description;
    private String logo_url;
    private String streaming_url;
    private String author;
    private int view_count;
    private long created;
    private List<Travel> photos;
    private List<News> relations;
    private List<News> neighbours;
    private List<News> interesting;
    private List<News> belongs;
    private DetailNewsDataFooter footer;
    private List<Tag> tags = new ArrayList<>();
    private String short_description;

    public DetailNewsDataFooter getFooter() {
        return footer;
    }

    public void setFooter(DetailNewsDataFooter footer) {
        this.footer = footer;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<News> getRelations() {
        return relations;
    }

    public void setRelations(List<News> relations) {
        this.relations = relations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<News> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<News> neighbours) {
        this.neighbours = neighbours;
    }

    public List<News> getInteresting() {
        return interesting;
    }

    public void setInteresting(List<News> interesting) {
        this.interesting = interesting;
    }

    public List<News> getBelongs() {
        return belongs;
    }

    public void setBelongs(List<News> belongs) {
        this.belongs = belongs;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getStreaming_url() {
        return streaming_url;
    }

    public void setStreaming_url(String streaming_url) {
        this.streaming_url = streaming_url;
    }

    public List<Travel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Travel> photos) {
        this.photos = photos;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public class DetailNewsDataFooter {
        private String url;
        private String description_wap;
        private String description;
        private String url_api;

        public String getUrl_api() {
            return url_api;
        }

        public void setUrl_api(String url_api) {
            this.url_api = url_api;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription_wap() {
            return description_wap;
        }

        public void setDescription_wap(String description_wap) {
            this.description_wap = description_wap;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class Tag {
        private String name;
        private String link;

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
