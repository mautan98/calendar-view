package com.namviet.vtvtravel.response.f2smalllocation;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class SortSmallLocationResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private List<Item> items;

        public List<Item> getItems() {
            return items;
        }

        public class Item {
            private String text;
            private String fields;
            private boolean isChecked;

            public void setText(String text) {
                this.text = text;
            }

            public void setFields(String fields) {
                this.fields = fields;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getText() {
                return text;
            }

            public String getFields() {
                return fields;
            }
        }
    }
}
