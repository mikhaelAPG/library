package com.example.library.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopBorrowedBookResponse {
    @JsonProperty("book_title")
    private String bookTitle;
    @JsonProperty("total_borrowed_count")
    private Long totalBorrowedCount;

    public TopBorrowedBookResponse(String bookTitle, Long totalBorrowedCount) {
        this.bookTitle = bookTitle;
        this.totalBorrowedCount = totalBorrowedCount;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Long getTotalBorrowedCount() {
        return totalBorrowedCount;
    }

    public void setTotalBorrowedCount(Long totalBorrowedCount) {
        this.totalBorrowedCount = totalBorrowedCount;
    }
}
