package com.namviet.vtvtravel.model.f2operator;

public class OperatorInformation {
    private String title;
    private String content;
    private boolean isShowTick;

    public OperatorInformation(String title, String content, boolean isShowTick) {
        this.title = title;
        this.content = content;
        this.isShowTick = isShowTick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowTick() {
        return isShowTick;
    }

    public void setShowTick(boolean showTick) {
        isShowTick = showTick;
    }
}
