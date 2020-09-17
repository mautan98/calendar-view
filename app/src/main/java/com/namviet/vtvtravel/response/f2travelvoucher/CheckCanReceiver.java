package com.namviet.vtvtravel.response.f2travelvoucher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckCanReceiver {

    @Expose
    @SerializedName("success")
    private boolean success;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("errorCode")
    private String errorCode;
    @Expose
    @SerializedName("data")
    private boolean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
