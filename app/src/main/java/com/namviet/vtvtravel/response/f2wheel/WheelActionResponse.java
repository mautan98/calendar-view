package com.namviet.vtvtravel.response.f2wheel;

import com.namviet.vtvtravel.response.BaseResponse;

public class WheelActionResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private WheelAward wheelAward;
        private String turn;

        public WheelAward getWheelAward() {
            return wheelAward;
        }

        public String getTurn() {
            return turn;
        }

        public class WheelAward{
            private String content;
            private String position;
            private String name;
            private String code;
            private String nameType;
            private String voucherId;

            public String getContent() {
                return content;
            }

            public String getPosition() {
                return position;
            }

            public String getName() {
                return name;
            }

            public String getCode() {
                return code;
            }

            public String getNameType() {
                return nameType;
            }

            public String getVoucherId() {
                return voucherId;
            }
        }
    }
}
