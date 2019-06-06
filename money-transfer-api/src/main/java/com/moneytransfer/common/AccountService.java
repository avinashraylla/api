package com.moneytransfer.common;

import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.AccountRecipientDTO;
import com.moneytransfer.common.dto.AccountTransactionDTO;
import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.AccountRecipients;
import com.moneytransfer.common.jpa.AccountTransactions;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    public Long createAccount(AccountDTO account) throws Exception;
    public AccountDTO updateAccount(AccountDTO account) throws Exception;
    public void deleteAccount(Long account) throws Exception;
    public void addRecipient(Long accountNumber,Long recipientAccount, String name) throws Exception;
    public void removeRecipient(Long accountNumber,Long recipientAccount) throws Exception;
    public List<AccountTransactionDTO> getAllTransfers(Long accountNumber) throws Exception;
    public List<AccountDTO> getAllAccounts() throws Exception;
    public AccountDTO getAccountById(Long accountNumber) throws Exception;
    public List<AccountRecipientDTO> getAccountRecipients(Long accountNumber) throws Exception;
    public BigDecimal deposit(Long accountNumber, BigDecimal amount) throws Exception;
    public BigDecimal withdraw(Long accountNumber, BigDecimal amount) throws Exception;
    public BigDecimal transferBalance(AccountTransactionDTO transaction) throws Exception;
}
