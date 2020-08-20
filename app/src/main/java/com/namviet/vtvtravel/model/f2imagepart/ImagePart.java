package com.namviet.vtvtravel.model.f2imagepart;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.imagepart.ImagePartResponse;

import java.util.List;

public class ImagePart {
    private List<Travel> items;

    public List<Travel> getItems() {
        return items;
    }

    public void setItems(List<Travel> items) {
        this.items = items;
    }
}
