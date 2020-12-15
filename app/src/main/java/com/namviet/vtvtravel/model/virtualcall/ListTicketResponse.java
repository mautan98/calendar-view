package com.namviet.vtvtravel.model.virtualcall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTicketResponse {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("requestId")
    @Expose
    private Object requestId;
    @SerializedName("success")
    @Expose
    private boolean success;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getRequestId() {
        return requestId;
    }

    public void setRequestId(Object requestId) {
        this.requestId = requestId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
