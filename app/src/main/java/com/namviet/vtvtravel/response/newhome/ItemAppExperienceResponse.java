package com.namviet.vtvtravel.response.newhome;

import com.namviet.vtvtravel.model.travelnews.Travel;
import com.namviet.vtvtravel.response.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class ItemAppExperienceResponse extends BaseResponse implements Serializable {

    private List<Travel> items;

    public List<Travel> getItems() {
        return items;
    }

}
