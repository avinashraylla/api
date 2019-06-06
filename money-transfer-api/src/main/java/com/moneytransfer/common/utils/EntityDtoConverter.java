package com.moneytransfer.common.utils;

import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.AccountRecipientDTO;
import com.moneytransfer.common.dto.AccountTransactionDTO;
import com.moneytransfer.common.dto.UserDTO;
import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.AccountRecipients;
import com.moneytransfer.common.jpa.AccountTransactions;
import com.moneytransfer.common.jpa.User;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDtoConverter {

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = new UserDTO();
            userDTO.setAddress(user.getAddress());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setUserId(user.getUserId());
        }

        return userDTO;
    }

    public static AccountDTO convertToAccountDTO(Account account){
        AccountDTO accountDTO = null;
        if(account!=null) {
            accountDTO = new AccountDTO();
            accountDTO.setAccountNumber(account.getAccountNumber());
            accountDTO.setAccountType(account.getAccountType());
            accountDTO.setBalance(account.getBalance());
            accountDTO.setCurrency(account.getCurrency());
            accountDTO.setUserId(account.getUser().getUserId());
            List<AccountRecipientDTO> recipients = null;
            if(account.getAccountRecipients()!=null && account.getAccountRecipients().size()>0) {
               recipients =  account.getAccountRecipients().stream().map(EntityDtoConverter::convertToRecipientDTO).collect(Collectors.toList());
            }
            accountDTO.setAccountRecipients(recipients);
        }
        return accountDTO;
    }

    public static AccountRecipientDTO convertToRecipientDTO(AccountRecipients accountRecipient){
        AccountRecipientDTO dto = null;
        if(accountRecipient!=null) {
            dto = new AccountRecipientDTO();
            dto.setId(accountRecipient.getId());
            dto.setAccountNumber(accountRecipient.getAccount().getAccountNumber());
            dto.setRecipientAccount(accountRecipient.getRecipientAccount().getAccountNumber());
            dto.setCurrency(accountRecipient.getCurrency());
            dto.setName(accountRecipient.getName());
        }

        return dto;
    }

    public static AccountTransactionDTO convertTransactionsToDTO(AccountTransactions transactions) {
        AccountTransactionDTO dto = null;
        if(transactions!=null) {
            dto = new AccountTransactionDTO();
            dto.setAmount(transactions.getAmount());
            dto.setId(transactions.getId());
            dto.setComments(transactions.getComments());
            dto.setDestinationAccount(transactions.getDestinationAccount());
            dto.setSourceAccount(transactions.getSourceAccount());
        }
        return dto;
    }

    public static AccountTransactions converDtoToTransactions(AccountTransactionDTO transactionDTO) {
        AccountTransactions transactions = null;
        if(transactionDTO!=null) {
            transactions = new AccountTransactions();
            transactions.setId(transactionDTO.getId());
            transactions.setAmount(transactionDTO.getAmount());
            transactions.setComments(transactionDTO.getComments());
            transactions.setSourceAccount(transactionDTO.getSourceAccount());
            transactions.setDestinationAccount(transactionDTO.getDestinationAccount());
            transactions.setLastModified(transactionDTO.getLastModified());
        }
        return transactions;
    }

    public static Account convertDtoToAccount(AccountDTO accountDTO) {
        Account account = null;
        if(accountDTO!=null) {
            account = new Account();
            account.setAccountNumber(accountDTO.getAccountNumber());
            account.setBalance(accountDTO.getBalance());
            account.setCurrency(accountDTO.getCurrency());
            account.setAccountType(accountDTO.getAccountType());
            List<AccountRecipients> recipients = null;
            if(accountDTO.getAccountRecipients()!=null && accountDTO.getAccountRecipients().size()>0) {
                recipients = accountDTO.getAccountRecipients().stream().map(EntityDtoConverter::convertDtoToRecipients).collect(Collectors.toList());
            }
            account.setAccountRecipients(recipients);
            User user = new User();
            user.setUserId(accountDTO.getUserId());
            account.setUser(user);

        }
        return account;
    }

    public static AccountRecipients convertDtoToRecipients(AccountRecipientDTO dto) {
        AccountRecipients recipient = null;
        if(dto!=null) {
            recipient = new AccountRecipients();
            recipient.setId(dto.getId());
            Account acc = new Account();
            acc.setAccountNumber(dto.getAccountNumber());
            recipient.setAccount(acc);
            Account acc1 = new Account();
            acc1.setAccountNumber(dto.getRecipientAccount());
            recipient.setRecipientAccount(acc1);
            recipient.setCurrency(dto.getCurrency());
            recipient.setName(dto.getName());
        }
        return recipient;
    }

    public static User convertDtoToUser(UserDTO userDto) {
        User user = null;
        if (userDto != null) {
            user = new User();
            user.setAddress(userDto.getAddress());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUserId(userDto.getUserId());

        }

        return user;
    }



}
