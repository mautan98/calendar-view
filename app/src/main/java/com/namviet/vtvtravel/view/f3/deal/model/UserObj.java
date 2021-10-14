package com.namviet.vtvtravel.view.f3.deal.model;

public class UserObj {
    private int icon;
    private String name;
    private boolean isAccept;

    public UserObj() {
    }

    public UserObj(int icon, String name, boolean isAccept) {
        this.icon = icon;
        this.name = name;
        this.isAccept = isAccept;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }
}
