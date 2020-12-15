package com.namviet.vtvtravel.model.virtualcall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sort {

    @SerializedName("unsorted")
    @Expose
    private boolean unsorted;
    @SerializedName("sorted")
    @Expose
    private boolean sorted;
    @SerializedName("empty")
    @Expose
    private boolean empty;

    public boolean isUnsorted() {
        return unsorted;
    }

    public void setUnsorted(boolean unsorted) {
        this.unsorted = unsorted;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

