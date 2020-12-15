package com.namviet.vtvtravel.model.chat;

public class Star {
    private boolean isSelected;

    public Star(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
