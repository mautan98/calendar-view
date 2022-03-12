package com.namviet.vtvtravel.view.f3.deal.model.dealfollow;

public class RewardStatus {
    private String id;
    private String label;
    private boolean isMarked;

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public RewardStatus(String id, String label, boolean isMarked) {
        this.id = id;
        this.label = label;
        this.isMarked = isMarked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
