package com.namviet.vtvtravel.f2errorresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    private String codeToSplitCase;

    public String getCodeToSplitCase() {
        return codeToSplitCase;
    }

    public void setCodeToSplitCase(String codeToSplitCase) {
        this.codeToSplitCase = codeToSplitCase;
    }

    @Expose
    @SerializedName("success")
    private boolean success;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("errorCode")
    private String errorCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
}
