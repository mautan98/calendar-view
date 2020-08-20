package com.baseapp.enity;

import java.io.Serializable;

/**
 * Created by vn on 12/16/2016.
 */

public class StackEntry implements Serializable {
    private String fragTag = null;

    public StackEntry(String fragTag) {
        super();
        this.fragTag = fragTag;
    }
    public String getFragTag() {
        return fragTag;
    }
}
