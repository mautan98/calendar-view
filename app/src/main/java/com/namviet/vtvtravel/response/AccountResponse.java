package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Account;

public class AccountResponse extends BaseResponse{
    private Account data;

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "data=" + data +
                '}';
    }
}
