package com.example.library.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AllTransactionResponse {
    @JsonProperty("borrowing_date")
    private Date borrowingDate;
    @JsonProperty("due_date")
    private Date dueDate;
    @JsonProperty("return_date")
    private Date returnDate;
    private Double penalty;

    public AllTransactionResponse(Date borrowingDate, Date dueDate, Date returnDate, Double penalty) {
        this.borrowingDate = borrowingDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.penalty = penalty;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }
}
