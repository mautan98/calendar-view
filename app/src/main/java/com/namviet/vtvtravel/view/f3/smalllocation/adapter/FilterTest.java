package com.namviet.vtvtravel.view.f3.smalllocation.adapter;

public class FilterTest {
    private String name;
    private int count;
    private boolean isSelected;

    public FilterTest(String name, int count, boolean isSelected) {
        this.name = name;
        this.count = count;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
