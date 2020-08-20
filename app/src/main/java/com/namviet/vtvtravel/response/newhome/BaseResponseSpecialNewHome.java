package com.namviet.vtvtravel.response.newhome;

import java.util.ArrayList;

public class BaseResponseSpecialNewHome {
    private String status;
    private String code;
    private String message;
    private String errorCode;
    private String msg;
    private boolean success;
    private ArrayList<AppDealResponse.Data> data;
    private String codeData;

    public ArrayList<AppDealResponse.Data> getData() {
        return data;
    }

    public void setData(ArrayList<AppDealResponse.Data> data) {
        this.data = data;
    }

    public String getCodeData() {
        return codeData;
    }

    public void setCodeData(String codeData) {
        this.codeData = codeData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }
}


