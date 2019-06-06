package com.moneytransfer.common.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name = "ACCOUNT_TRANSACTIONS")
public class AccountTransactions  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "SOURCE_ACCOUNT")
    private Long sourceAccount;
    @Column(name = "DEST_ACCOUNT")
    private Long destinationAccount;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "LAST_MODIFIED")
    private Date lastModified;

    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
