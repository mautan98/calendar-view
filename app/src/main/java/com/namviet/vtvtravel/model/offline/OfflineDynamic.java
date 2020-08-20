package com.namviet.vtvtravel.model.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class OfflineDynamic {

    @Expose
    @SerializedName("listScreens")
    private List<ListScreen> listScreens;
    @Expose
    @SerializedName("popupScreen")
    private PopupScreen popupScreen;

    public List<ListScreen> getListScreens() {
        return listScreens;
    }

    public void setListScreens(List<ListScreen> listScreens) {
        this.listScreens = listScreens;
    }

    public PopupScreen getPopupScreen() {
        return popupScreen;
    }

    public void setPopupScreen(PopupScreen popupScreen) {
        this.popupScreen = popupScreen;
    }
}
