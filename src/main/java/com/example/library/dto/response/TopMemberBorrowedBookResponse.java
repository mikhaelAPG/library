package com.example.library.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopMemberBorrowedBookResponse {
    @JsonProperty("member_name")
    private String memberName;
    @JsonProperty("total_borrowed_count")
    private Long totalBorrowedCount;

    public TopMemberBorrowedBookResponse(String memberName, Long totalBorrowedCount) {
        this.memberName = memberName;
        this.totalBorrowedCount = totalBorrowedCount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getTotalBorrowedCount() {
        return totalBorrowedCount;
    }

    public void setTotalBorrowedCount(Long totalBorrowedCount) {
        this.totalBorrowedCount = totalBorrowedCount;
    }
}
