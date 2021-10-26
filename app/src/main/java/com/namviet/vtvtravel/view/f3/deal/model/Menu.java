package com.namviet.vtvtravel.view.f3.deal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Menu {
    @Expose
    @SerializedName("menu_ctkm")
    private ArrayList<Block> listMenuCTKM;

    public ArrayList<Block> getListMenuCTKM() {
        return listMenuCTKM;
    }

    public void setListMenuCTKM(ArrayList<Block> listMenuCTKM) {
        this.listMenuCTKM = listMenuCTKM;
    }
}
