package com.namviet.vtvtravel.model.virtualcall;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("items")
    @Expose
    private List<VirtualTicket> content = null;
    @SerializedName("pageable")
    @Expose
    private Pageable pageable;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("last")
    @Expose
    private boolean last;
    @SerializedName("total")
    @Expose
    private int totalElements;
    @SerializedName("first")
    @Expose
    private boolean first;
    @SerializedName("sort")
    @Expose
    private Sort sort;
    @SerializedName("numberOfElements")
    @Expose
    private int numberOfElements;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("empty")
    @Expose
    private boolean empty;
    @SerializedName("numberRequested")
    @Expose
    private int numberRequested;
    @SerializedName("numberProcessing")
    @Expose
    private int numberProcessing;

    public List<VirtualTicket> getContent() {
        return content;
    }

    public void setContent(List<VirtualTicket> content) {
        this.content = content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getNumberRequested() {
        return numberRequested;
    }

    public void setNumberRequested(int numberRequested) {
        this.numberRequested = numberRequested;
    }

    public int getNumberProcessing() {
        return numberProcessing;
    }

    public void setNumberProcessing(int numberProcessing) {
        this.numberProcessing = numberProcessing;
    }

}
