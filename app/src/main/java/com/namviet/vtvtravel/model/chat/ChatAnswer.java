package com.namviet.vtvtravel.model.chat;

public class ChatAnswer{
    private String address;
    private String content_type;
//    private float distance;
    private String name;
    private String short_description;
    private String logo_url;
    private String detail_link;
    private String id;

    private String banner_url;
    private float date;
    private String date_str;
    private float humidity;
    private float pressure;
    private String region_banner;
    private String region_id;
    private String region_name;
    private float temp;
    private float temp_max;
    private float temp_min;
    Weather weather;
    Wind wind;

    private String from;
    private String to;
    private String iframe;


    public String getAddress() {
        return address;
    }

    public String getContent_type() {
        return content_type;
    }

//    public float getDistance() {
//        return distance;
//    }

    public String getName() {
        return name;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public String getId() {
        return id;
    }

    // Setter Methods

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

//    public void setDistance(float distance) {
//        this.distance = distance;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public void setId(String id) {
        this.id = id;
    }
    // Getter Methods

    public String getBanner_url() {
        return banner_url;
    }

    public float getDate() {
        return date;
    }

    public String getDate_str() {
        return date_str;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public String getRegion_banner() {
        return region_banner;
    }

    public String getRegion_id() {
        return region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public float getTemp() {
        return temp;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public Weather getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    // Setter Methods

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public void setDate(float date) {
        this.date = date;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setRegion_banner(String region_banner) {
        this.region_banner = region_banner;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public void setWeather(Weather weatherObject) {
        this.weather = weatherObject;
    }

    public void setWind(Wind windObject) {
        this.wind = windObject;
    }

    public class Weather {
        private String description;
        private String icon;
        private String icon_url;
        private float id;
        private String main;

        // Getter Methods

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public float getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        // Setter Methods

        public void setDescription(String description) {
            this.description = description;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public void setId(float id) {
            this.id = id;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }

    public class Wind {
        private float deg;
        private float speed;

        // Getter Methods

        public float getDeg() {
            return deg;
        }

        public float getSpeed() {
            return speed;
        }

        // Setter Methods

        public void setDeg(float deg) {
            this.deg = deg;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getIframe() {
        return iframe;
    }

    // Setter Methods

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }
}
