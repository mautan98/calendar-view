package com.namviet.vtvtravel.response.f2comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.namviet.vtvtravel.response.BaseResponse;

public class CheckShowCaptcha extends BaseResponse {

    private String parentId;
    private String content;
    private String contentId;
    private String contentType;

    private Data data;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @Expose
        @SerializedName("isShowCaptcha")
        private boolean captcha;
        @Expose
        @SerializedName("isSensitiveWord")
        private boolean sensitiveWord;

        public boolean isCaptcha() {
            return captcha;
        }

        public void setCaptcha(boolean captcha) {
            this.captcha = captcha;
        }

        public boolean isSensitiveWord() {
            return sensitiveWord;
        }

        public void setSensitiveWord(boolean sensitiveWord) {
            this.sensitiveWord = sensitiveWord;
        }
    }
}
