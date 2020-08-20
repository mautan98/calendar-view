package com.daimajia.slider.library;


import java.util.List;

public class ItemGroup {
    private List<Travel> group;

    public ItemGroup(List<Travel> group) {
        this.group = group;
    }

    public List<Travel> getGroup() {
        return group;
    }

    public void setGroup(List<Travel> group) {
        this.group = group;
    }
}
