package com.moneytransfer.common;

import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.AccountRecipients;
import com.moneytransfer.common.jpa.AccountTransactions;

import java.math.BigDecimal;
import java.util.List;

public interface AccountsDAO {
    public Long createAccount(Account account) throws Exception;
    public Account updateAccount(Account account) throws Exception;
    public void deleteAccount(Long account) throws Exception;
    public void addRecipient(Long accountNumber,Long recipientAccount, String name) throws Exception;
    public void removeRecipient(Long accountNumber,Long recipientAccount) throws Exception;
    public List<AccountTransactions> getAllTransfers(Long accountNumber) throws Exception;
    public List<Account> getAllAccounts() throws Exception;
    public Account getAccountById(Long accountNumber) throws Exception;
    public List<AccountRecipients> getAccountRecipients(Long accountNumber) throws Exception;
    public BigDecimal deposit(Long accountNumber, BigDecimal amount) throws Exception;
    public BigDecimal withdraw(Long accountNumber, BigDecimal amount) throws Exception;
    public BigDecimal transferBalance(AccountTransactions transaction) throws Exception;
}
