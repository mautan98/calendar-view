package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.response.BaseResponse;

import java.util.List;

public class SettingResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private Menu menus;

        public Menu getMenus() {
            return menus;
        }

        public class Menu {
            private List<Item> left;
            private List<Item> footer;

            public List<Item> getLeft() {
                return left;
            }

            public List<Item> getFooter() {
                return footer;
            }

            public class Item {
                private String id;
                private String code;
                private String name;
                private String icon_url;
                private String icon_enable_url;
                private String link;

                public String getId() {
                    return id;
                }

                public String getCode() {
                    return code;
                }

                public String getName() {
                    return name;
                }

                public String getIcon_url() {
                    return icon_url;
                }

                public String getIcon_enable_url() {
                    return icon_enable_url;
                }

                public String getLink() {
                    return link;
                }
            }
        }
    }
}
