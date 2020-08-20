package com.namviet.vtvtravel.view.fragment.f2service;

import com.namviet.vtvtravel.model.Account;
import com.namviet.vtvtravel.response.BaseResponse;

public class GetInfoResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        Account user;

        public Account getUser() {
            return user;
        }

        public void setUser(Account user) {
            this.user = user;
        }
    }
}
