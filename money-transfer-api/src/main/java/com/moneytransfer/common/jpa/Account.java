package com.moneytransfer.common.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {
    @Id
    @Column(name = "ACCOUNT_NUMBER")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @ManyToOne()
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "ACCOUNT_TYPE")
    private int accountType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account",cascade = {CascadeType.ALL,CascadeType.PERSIST, CascadeType.MERGE},
    orphanRemoval = true)
    @JsonIgnore
    private List<AccountRecipients> accountRecipients;

    @Version
    private Integer version;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
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

    public List<AccountRecipients> getAccountRecipients() {
        return accountRecipients;
    }

    public void setAccountRecipients(List<AccountRecipients> accountRecipients) {
        this.accountRecipients = accountRecipients;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", user=" + user +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", accountType=" + accountType +
                ", accountRecipients=" + accountRecipients +
                ", version=" + version +
                '}';
    }
}
