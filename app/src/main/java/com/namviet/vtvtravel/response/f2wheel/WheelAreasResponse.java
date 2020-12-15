package com.namviet.vtvtravel.response.f2wheel;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class WheelAreasResponse extends BaseResponse {
    private List<Item> data;

    public List<Item> getData() {
        return data;
    }

    public class Item {
        private String name;
        private String position;
        private String wheelAwardTypeId;
        private String voucherId;
        private String logoUri;

        public String getName() {
            return name;
        }

        public String getPosition() {
            return position;
        }

        public String getWheelAwardTypeId() {
            return wheelAwardTypeId;
        }

        public String getVoucherId() {
            return voucherId;
        }

        public String getLogo() {
            return logoUri;
        }
    }
}
