package com.namviet.vtvtravel.response.f2livetv;

import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class LiveTvResponse extends BaseResponse implements Serializable {

    private String widget_class;
    private String widget_type;
    private String description;

    private List<Channel> items;

    public String getWidget_class() {
        return widget_class;
    }

    public String getWidget_type() {
        return widget_type;
    }

    public List<Channel> getItems() {
        return items;
    }

    public String getDescription() {
        return description;
    }

    public class Channel implements Serializable {
        private String code;
        private String logo_url;
        private String logo_disabled;
        private String id;
        private String name;
        private String content_type;
        private String banner_url;
        private String detail_link;
        private List<Streaming> streaming_urls;
        private String schedule_link;
        private String date;
        private List<Schedule> schedule;

        public void setCode(String code) {
            this.code = code;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getLogo_disabled() {
            return logo_disabled;
        }

        public void setLogo_disabled(String logo_disabled) {
            this.logo_disabled = logo_disabled;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public void setDetail_link(String detail_link) {
            this.detail_link = detail_link;
        }

        public void setStreaming_urls(List<Streaming> streaming_urls) {
            this.streaming_urls = streaming_urls;
        }

        public void setSchedule_link(String schedule_link) {
            this.schedule_link = schedule_link;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setSchedule(List<Schedule> schedule) {
            this.schedule = schedule;
        }

        public String getCode() {
            return code;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getContent_type() {
            return content_type;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public String getDetail_link() {
            return detail_link;
        }

        public List<Streaming> getStreaming_urls() {
            return streaming_urls;
        }

        public String getSchedule_link() {
            return schedule_link;
        }

        public String getDate() {
            return date;
        }

        public List<Schedule> getSchedule() {
            return schedule;
        }

        public class Streaming implements Serializable {
            private String id;
            private String content_type;
            private String protocol;
            private String quality;
            private String url;

            public String getId() {
                return id;
            }

            public String getContent_type() {
                return content_type;
            }

            public String getProtocol() {
                return protocol;
            }

            public String getQuality() {
                return quality;
            }

            public String getUrl() {
                return url;
            }
        }

        public class Schedule implements Serializable {
            private String id;
            private String content_type;
            private String topic;
            private String name;
            private long start_time;
            private long end_time;

            public String getId() {
                return id;
            }

            public String getContent_type() {
                return content_type;
            }

            public String getTopic() {
                return topic;
            }

            public String getName() {
                return name;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }
        }
    }
}
