package com.moneytransfer.common.jpa;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "ACCOUNT_RECIPIENTS")
public class AccountRecipients implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CURRENCY")
    private String currency;
    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_NUMBER",nullable = false)
    private Account account;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "RECIPIENT_ACCOUNT")
    private Account recipientAccount;
    @Column(name = "NAME")
    private String name;

    public Account getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(Account recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public Long getId() {
        return id;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AccountRecipients{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", version=" + version +
                ", account=" + account.getAccountNumber() +
                ", recipientAccount=" + recipientAccount.getAccountNumber() +
                '}';
    }
}
