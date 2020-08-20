package com.namviet.vtvtravel.response;

import com.namviet.vtvtravel.model.Comment;

import java.util.List;

public class CommentData {
    private int totalPages;
    private List<Comment> content;
    private int totalElements;

    public List<Comment> getContent() {
        return content;
    }

    public void setContent(List<Comment> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
