package com.example.library.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopLateReturnResponse {
    @JsonProperty("member_name")
    private String memberName;
    @JsonProperty("total_borrowed_count")
    private Integer lateReturnsCount;

    public TopLateReturnResponse(String memberName, Integer lateReturnsCount) {
        this.memberName = memberName;
        this.lateReturnsCount = lateReturnsCount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getLateReturnsCount() {
        return lateReturnsCount;
    }

    public void setLateReturnsCount(Integer lateReturnsCount) {
        this.lateReturnsCount = lateReturnsCount;
    }
}
