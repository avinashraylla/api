package com.moneytransfer.common.dto;

public class AccountRecipientDTO {
    private Long id;
    private String currency;
    private Long accountNumber;
    private String name;
    private Long recipientAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(Long recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "AccountRecipientDTO{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", accountNumber=" + accountNumber +
                ", recipientAccount=" + recipientAccount +
                '}';
    }
}
