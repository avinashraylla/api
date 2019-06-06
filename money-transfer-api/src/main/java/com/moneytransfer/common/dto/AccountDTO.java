package com.moneytransfer.common.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountDTO {
    private Long accountNumber;
    private Long userId;
    private BigDecimal balance;
    private String currency;
    private int accountType;
    private List<AccountRecipientDTO> accountRecipients;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public List<AccountRecipientDTO> getAccountRecipients() {
        return accountRecipients;
    }

    public void setAccountRecipients(List<AccountRecipientDTO> accountRecipients) {
        this.accountRecipients = accountRecipients;
    }
}
