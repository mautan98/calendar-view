package com.namviet.vtvtravel.model.f2booking;

import com.namviet.vtvtravel.model.ItemMenu;
import com.namviet.vtvtravel.response.f2menu.MenuItem;

import java.util.ArrayList;

public class DataHelpCenter {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ArrayList<MenuItem> itemMenus;

    public ArrayList<MenuItem> getItemMenus() {
        return itemMenus;
    }

    public void setItemMenus(ArrayList<MenuItem> itemMenus) {
        this.itemMenus = itemMenus;
    }
}
