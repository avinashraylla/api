package com.moneytransfer;

import com.moneytransfer.common.AccountService;
import com.moneytransfer.common.UserService;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.AccountRecipientDTO;
import com.moneytransfer.common.dto.AccountTransactionDTO;
import com.moneytransfer.common.dto.UserDTO;
import com.moneytransfer.common.jpa.AccountTransactions;
import com.moneytransfer.services.AccountsServiceImpl;
import com.moneytransfer.services.UserServiceImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountServiceTest {
    private static org.h2.tools.Server h2Server;
    private static UserService userService;
    private static UserDTO user;
    private static AccountService accountService;

    @BeforeClass
    public static void setUp() throws Exception {
        h2Server = App.startH2Db();
        App.initializeDatabase();
        userService = new UserServiceImpl();
        accountService = new AccountsServiceImpl();
        user = new UserDTO();
        user.setLastName("Alpha");
        user.setFirstName("Beta");
        user.setAddress("Chicago");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        h2Server.stop();
    }

    @Test
    public void createAccount() throws Exception{
        AccountDTO accountDTO = new AccountDTO();
        AccountRecipientDTO recipientDTO = new AccountRecipientDTO();
        recipientDTO.setCurrency("USD");
        recipientDTO.setRecipientAccount(3L);
        recipientDTO.setAccountNumber(accountDTO.getAccountNumber());
        List<AccountRecipientDTO> recipients = new ArrayList<>();
        recipients.add(recipientDTO);
        accountDTO.setAccountRecipients(recipients);
        accountDTO.setBalance(BigDecimal.valueOf(400000));
        accountDTO.setUserId(2L);
        accountDTO.setAccountType(1);


        Long response = accountService.createAccount(accountDTO);
        System.out.println(response);

    }
    @Test
    public void updateAccount() throws Exception{
        AccountDTO accountDTO = accountService.getAccountById(2L);
        accountDTO.setBalance(BigDecimal.valueOf(2345678));
        AccountDTO response = accountService.updateAccount(accountDTO);
        Assert.assertNotNull(response);
        Assert.assertEquals(0,BigDecimal.valueOf(2345678).compareTo(response.getBalance()));
    }
    @Test(expected = Exception.class)
    public void deleteAccount() throws Exception{
        accountService.deleteAccount(5L);
        AccountDTO response = accountService.getAccountById(5L);
    }

    @Test
    public void addRecipient() throws Exception{
        accountService.addRecipient(2L,3L,"test");
        List<AccountRecipientDTO> recipients =  accountService.getAccountRecipients(2L);
        Assert.assertNotNull(recipients);
    }

    @Test
    public void removeRecipient() throws Exception{
        accountService.removeRecipient(3L,4L);
        List<AccountRecipientDTO> recipients = accountService.getAccountRecipients(3L);
        Assert.assertEquals(1,recipients.size());
    }
    @Test
    public void getAllTransfers() throws Exception{
        List transactions = accountService.getAllTransfers(2L);

    }

    @Test
    public void getAllAccounts() throws Exception{
        List<AccountDTO> accounts = accountService.getAllAccounts();
        Assert.assertNotNull(accounts);
    }
    @Test
    public void getAccountById() throws Exception{
        AccountDTO account = accountService.getAccountById(1L);
        Assert.assertNotNull(account);
        Assert.assertEquals(1,account.getAccountNumber().intValue());
    }

    @Test
    public void getAccountRecipients() throws Exception{
        List<AccountRecipientDTO> recipients = accountService.getAccountRecipients(3L);
        Assert.assertNotNull(recipients);
    }
    @Test
    public void deposit() throws Exception{
        AccountDTO account = accountService.getAccountById(2L);
        BigDecimal amount = accountService.deposit(2L,BigDecimal.valueOf(5000));
        Assert.assertNotNull(amount);
        Assert.assertEquals(amount,account.getBalance().add(BigDecimal.valueOf(5000)));
    }

    @Test
    public void withdraw() throws Exception{
        AccountDTO account = accountService.getAccountById(1L);
        BigDecimal amount = accountService.withdraw(1L,BigDecimal.valueOf(5000));
        AccountDTO after = accountService.getAccountById(1L);
        Assert.assertNotNull(amount);
        Assert.assertEquals(account.getBalance().subtract(BigDecimal.valueOf(5000)), after.getBalance());
    }
    @Test
    public void transferBalance() throws Exception{
        AccountDTO sourceAccount = accountService.getAccountById(2L);

        AccountDTO destAccountBefore = accountService.getAccountById(3L);
        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setSourceAccount(2L);
        transactionDTO.setDestinationAccount(3L);
        transactionDTO.setComments("bill pay");
        transactionDTO.setAmount(BigDecimal.valueOf(5000));

        BigDecimal amount = accountService.transferBalance(transactionDTO);

        AccountDTO destAccount = accountService.getAccountById(3L);
        List<AccountTransactionDTO> transactions = accountService.getAllTransfers(2L);
        Assert.assertNotNull(amount);
        Assert.assertEquals(amount,sourceAccount.getBalance().subtract(BigDecimal.valueOf(5000)));
        Assert.assertEquals(destAccount.getBalance(),destAccountBefore.getBalance().add(BigDecimal.valueOf(5000)));
        Assert.assertNotNull(transactions);

    }

}
