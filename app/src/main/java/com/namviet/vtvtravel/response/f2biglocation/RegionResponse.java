package com.namviet.vtvtravel.response.f2biglocation;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class RegionResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private String id;
        private String name;
        private String code_name;
        private String country_code;
        private Loc loc;
        private String zip_code;
        private String description;
        private String phone_local_region;
        private String address;
        private String area_code;
        private String banner_url;
        private List<String> thumb_url;
        private String banner_greeting;
        private String view_count;
        private String created;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode_name() {
            return code_name;
        }

        public String getCountry_code() {
            return country_code;
        }

        public Loc getLoc() {
            return loc;
        }

        public String getZip_code() {
            return zip_code;
        }

        public String getDescription() {
            return description;
        }

        public String getPhone_local_region() {
            return phone_local_region;
        }

        public String getAddress() {
            return address;
        }

        public String getArea_code() {
            return area_code;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public List<String> getThumb_url() {
            return thumb_url;
        }

        public String getBanner_greeting() {
            return banner_greeting;
        }

        public String getView_count() {
            return view_count;
        }

        public String getCreated() {
            return created;
        }

        public class Loc {
            private String type;
            private List<String> coordinates;

            public String getType() {
                return type;
            }

            public List<String> getCoordinates() {
                return coordinates;
            }
        }
    }
}
