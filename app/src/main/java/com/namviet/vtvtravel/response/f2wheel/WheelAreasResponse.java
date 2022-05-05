package com.namviet.vtvtravel.response.f2wheel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class WheelAreasResponse extends BaseResponse {
    private Data data;

    public Data getData() {
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


    public class Data {
        @Expose
        @SerializedName("wheelAreas")
        private List<Item> data;

        private String turn;

        public List<Item> getData() {
            return data;
        }

        public String getTurn() {
            return turn;
        }

        public void setTurn(String turn) {
            this.turn = turn;
        }
    }
}
