package com.moneytransfer.common.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AccountTransactionDTO {
    private Long id;
    private Long sourceAccount;
    private Long destinationAccount;
    private BigDecimal amount;
    private String comments;
    private Date lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Long sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Long getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Long destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
