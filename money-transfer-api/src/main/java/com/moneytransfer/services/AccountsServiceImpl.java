package com.moneytransfer.services;

import com.moneytransfer.common.AccountService;
import com.moneytransfer.common.AccountsDAO;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.AccountRecipientDTO;
import com.moneytransfer.common.dto.AccountTransactionDTO;
import com.moneytransfer.common.jpa.Account;
import static com.moneytransfer.common.utils.EntityDtoConverter.*;

import com.moneytransfer.common.jpa.AccountRecipients;
import com.moneytransfer.common.jpa.AccountTransactions;
import com.moneytransfer.common.utils.EntityDtoConverter;
import com.moneytransfer.dao.AccountDAOImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AccountsServiceImpl implements AccountService {
    private AccountsDAO accountsDAO = new AccountDAOImpl();
    @Override
    public Long createAccount(AccountDTO accountDTO) throws Exception {
        Account account = convertDtoToAccount(accountDTO);
        if(account.getAccountRecipients()!=null) {
            account.getAccountRecipients().forEach(accountRecipients -> {
                accountRecipients.setAccount(account);
            });
        }
        Long accountNumber = accountsDAO.createAccount(account);
        return accountNumber;
    }

    @Override
    public AccountDTO updateAccount(AccountDTO accountDTO) throws Exception {
        Account account = convertDtoToAccount(accountDTO);
        Account response = accountsDAO.updateAccount(account);
        return convertToAccountDTO(response);
    }

    @Override
    public void deleteAccount(Long account) throws Exception {
        accountsDAO.deleteAccount(account);
    }

    @Override

    public void addRecipient(Long accountNumber,Long recipientAccount, String name) throws Exception {
        accountsDAO.addRecipient(accountNumber,recipientAccount,name);
    }

    @Override
    public void removeRecipient(Long accountNumber, Long recipientAccount) throws Exception {
        accountsDAO.removeRecipient(accountNumber,recipientAccount);
    }


    @Override
    public List<AccountTransactionDTO> getAllTransfers(Long accountNumber) throws Exception {
        List<AccountTransactions> accountTransactions = accountsDAO.getAllTransfers(accountNumber);
        List<AccountTransactionDTO> result = null;
        if(accountTransactions!=null && accountTransactions.size()>0) {
            result = accountTransactions.stream().map(EntityDtoConverter::convertTransactionsToDTO).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public List<AccountDTO> getAllAccounts() throws Exception {
        List<Account> accounts = accountsDAO.getAllAccounts();
        List<AccountDTO> result = null;
        if(accounts!=null && accounts.size()>0) {
            result = accounts.stream().map(EntityDtoConverter::convertToAccountDTO).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public AccountDTO getAccountById(Long accountNumber) throws Exception {
        return convertToAccountDTO(accountsDAO.getAccountById(accountNumber));
    }

    @Override
    public List<AccountRecipientDTO> getAccountRecipients(Long accountNumber) throws Exception {
        List<AccountRecipients> recipients = accountsDAO.getAccountRecipients(accountNumber);
        List<AccountRecipientDTO> result = null;
        if(recipients!=null && recipients.size()>0) {
            result = recipients.stream().map(EntityDtoConverter::convertToRecipientDTO).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public BigDecimal deposit(Long accountNumber, BigDecimal amount) throws Exception {
        return accountsDAO.deposit(accountNumber,amount);
    }

    @Override
    public BigDecimal withdraw(Long accountNumber, BigDecimal amount) throws Exception {
        return accountsDAO.withdraw(accountNumber,amount);
    }

    @Override
    public BigDecimal transferBalance(AccountTransactionDTO transaction) throws Exception {
        return accountsDAO.transferBalance(converDtoToTransactions(transaction));
    }
}
