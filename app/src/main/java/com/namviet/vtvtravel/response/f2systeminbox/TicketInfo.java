package com.namviet.vtvtravel.response.f2systeminbox;

import com.namviet.vtvtravel.response.BaseResponse;

public class TicketInfo extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private String ticket_id;
        private String subscriber_name;
        private String subscriber_mobile;
        private String description_require;
        private String status;
        private String id;

        public String getTicket_id() {
            return ticket_id;
        }

        public String getSubscriber_name() {
            return subscriber_name;
        }

        public String getSubscriber_mobile() {
            return subscriber_mobile;
        }

        public String getDescription_require() {
            return description_require;
        }

        public String getStatus() {
            return status;
        }

        public String getId() {
            return id;
        }
    }
}
