package com.namviet.vtvtravel.response.f2chat;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class HelloMessageResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data{
        private String onbot;
        private List<Item> items;

        public String getOnbot() {
            return onbot;
        }

        public List<Item> getItems() {
            return items;
        }

        public class Item {
            private String id;
            private String code;
            private String descriptions;
            private String icon;
            private String onbot;
            private String type;
            private String label;
            private String parentId;
            private String status;

            public String getId() {
                return id;
            }

            public String getCode() {
                return code;
            }

            public String getDescriptions() {
                return descriptions;
            }

            public String getIcon() {
                return icon;
            }

            public String getOnbot() {
                return onbot;
            }

            public String getType() {
                return type;
            }

            public String getLabel() {
                return label;
            }

            public String getParentId() {
                return parentId;
            }

            public String getStatus() {
                return status;
            }
        }
    }
}
