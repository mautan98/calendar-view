
package com.namviet.vtvtravel.view.f3.deal.model.dealbycampaign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Pageable {

    @SerializedName("sort")
    @Expose
    private Sort sort;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("unpaged")
    @Expose
    private Boolean unpaged;
    @SerializedName("paged")
    @Expose
    private Boolean paged;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Boolean getUnpaged() {
        return unpaged;
    }

    public void setUnpaged(Boolean unpaged) {
        this.unpaged = unpaged;
    }

    public Boolean getPaged() {
        return paged;
    }

    public void setPaged(Boolean paged) {
        this.paged = paged;
    }

}
