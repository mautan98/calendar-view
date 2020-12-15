package com.namviet.vtvtravel.model.virtualcall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pageable {

    @SerializedName("sort")
    @Expose
    private Sort sort;
    @SerializedName("pageSize")
    @Expose
    private int pageSize;
    @SerializedName("pageNumber")
    @Expose
    private int pageNumber;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("paged")
    @Expose
    private boolean paged;
    @SerializedName("unpaged")
    @Expose
    private boolean unpaged;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isPaged() {
        return paged;
    }

    public void setPaged(boolean paged) {
        this.paged = paged;
    }

    public boolean isUnpaged() {
        return unpaged;
    }

    public void setUnpaged(boolean unpaged) {
        this.unpaged = unpaged;
    }

}
