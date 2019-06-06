package com.moneytransfer.dao;

import com.moneytransfer.common.AccountsDAO;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.AccountRecipients;
import com.moneytransfer.common.jpa.AccountTransactions;
import com.moneytransfer.common.jpa.User;
import com.moneytransfer.common.utils.EntityDtoConverter;

import static com.moneytransfer.common.utils.EntityManagerUtil.*;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

public class AccountDAOImpl implements AccountsDAO {

    @Override
    public Long createAccount(Account account) throws Exception {
        final EntityManager em = getEntityManager();;
        try {
            em.getTransaction().begin();
            User user = em.find(User.class,account.getUser().getUserId());
            account.setUser(user);
            if(account.getAccountRecipients()!=null) {
                account.getAccountRecipients().forEach(accountRecipients -> {
                    accountRecipients.setAccount(account);
                    Account recipeint = em.find(Account.class,accountRecipients.getRecipientAccount().getAccountNumber());
                    accountRecipients.setRecipientAccount(recipeint);
                });
            }
            em.persist(account);
            em.flush();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }
        return account.getAccountNumber();
    }

    @Override
    public Account updateAccount(Account account) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account acc = em.find(Account.class,account.getAccountNumber(),LockModeType.PESSIMISTIC_WRITE);
            acc.setAccountType(account.getAccountType());
            acc.setBalance(account.getBalance());
            acc.setCurrency(account.getCurrency());

            em.getTransaction().commit();

        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw  e;
        }
        finally {
           close(em);
        }

        return account;
    }

    @Override
    public void deleteAccount(Long accountNumber) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account account = em.find(Account.class,accountNumber);
            em.remove(account);
            em.getTransaction().commit();
            close(em);
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }
    }

    @Override
    public void addRecipient(Long accountNumber, Long recipientAccount, String name) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account sourceAccount = em.find(Account.class,accountNumber,LockModeType.PESSIMISTIC_WRITE);
            AccountRecipients recipient = new AccountRecipients();
            recipient.setCurrency(sourceAccount.getCurrency());
            Account recipientAcc = em.find(Account.class,recipientAccount,LockModeType.PESSIMISTIC_WRITE);
            recipient.setRecipientAccount(recipientAcc);
            recipient.setAccount(sourceAccount);
            em.persist(recipient);
            em.getTransaction().commit();
            close(em);
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }
    }

    @Override
    public void removeRecipient(Long accountNumber,Long recipientAccount) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
               int count = em.createQuery("delete from AccountRecipients where account.accountNumber=:accountNumber and recipientAccount.accountNumber=:recipientAccount")
                    .setParameter("accountNumber",accountNumber)
                    .setParameter("recipientAccount",recipientAccount)
                    .executeUpdate();
            if(count==0){
                em.getTransaction().rollback();
            }
            else {
                em.getTransaction().commit();
            }
            close(em);
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }
    }

    @Override
    public List<AccountTransactions> getAllTransfers(Long accountNumber) throws Exception {

        EntityManager em = null;
        List<AccountTransactions> result = null;
        try {
            em = getEntityManager();
            result = em.createQuery("from AccountTransactions a where a.sourceAccount = :accountNumber ").setParameter("accountNumber", accountNumber).getResultList();
            close(em);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return result;
    }

    @Override
    public List<Account> getAllAccounts() throws Exception {
        EntityManager em = null;
        List<Account> result = null;
        try {
            em = getEntityManager();
            result = em.createQuery("from Account a").getResultList();
            close(em);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return result;
    }

    @Override
    public Account getAccountById(Long accountNumber) throws Exception {
        EntityManager em = null;
        Account result = null;
        try {
            em = getEntityManager();
            result = (Account) em.createQuery("from Account a where a.accountNumber = :accountNumber").setParameter("accountNumber", accountNumber).getSingleResult();
            em.close();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return result;
    }

    @Override
    public List<AccountRecipients> getAccountRecipients(Long accountNumber) throws Exception {
        EntityManager em = null;
        List<AccountRecipients> result = null;
        try {
            em = getEntityManager();
            result = em.createQuery("from AccountRecipients a where a.account.accountNumber = :accountNumber").setParameter("accountNumber",accountNumber).getResultList();
            close(em);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return result;
    }

    @Override
    public BigDecimal deposit(Long accountNumber, BigDecimal amount) throws Exception {
        EntityManager em = null;
        BigDecimal result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account account = (Account)em.createQuery("from Account a where a.accountNumber = :accountNumber")
                    .setParameter("accountNumber",accountNumber).getSingleResult();
            em.lock(account,LockModeType.PESSIMISTIC_WRITE);
            account.setBalance(account.getBalance().add(amount));
            result = account.getBalance();
            em.getTransaction().commit();
            em.close();
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }

        return result;
    }

    @Override
    public BigDecimal withdraw(Long accountNumber, BigDecimal amount) throws Exception {
        EntityManager em = null;
        BigDecimal result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account account = (Account)em.find(Account.class,accountNumber,LockModeType.PESSIMISTIC_WRITE);

            if(account.getBalance().compareTo(BigDecimal.ZERO)<=0) {
                em.getTransaction().rollback();
                throw new Exception("Insufficient funds");
            }
            account.setBalance(account.getBalance().subtract(amount));
            result = account.getBalance();
            em.getTransaction().commit();
            close(em);
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }
        return result;
    }

    @Override
    public BigDecimal transferBalance(AccountTransactions transaction) throws Exception {
        EntityManager em = null;
        BigDecimal result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account account1 = (Account)em.find(Account.class,transaction.getSourceAccount(),LockModeType.PESSIMISTIC_WRITE);
            Account account2 = (Account)em.find(Account.class,transaction.getDestinationAccount(),LockModeType.PESSIMISTIC_WRITE);

            AccountTransactions transactions = new AccountTransactions();
            if(account1.getBalance() == null || account1.getBalance().subtract(transaction.getAmount()).compareTo(BigDecimal.ZERO)<=0) {
                em.getTransaction().rollback();
                throw new Exception("Insufficient funds");
            }
            else {
                account1.setBalance(account1.getBalance().subtract(transaction.getAmount()));
                if(account2.getBalance()!=null) {
                    account2.setBalance(account2.getBalance().add(transaction.getAmount()));
                }
                else {
                    account2.setBalance(transaction.getAmount());
                }

                em.persist(transaction);

            }

            result = account1.getBalance();
            em.getTransaction().commit();
            em.close();
        }
        catch (Exception e) {
            rollbackAndClose(em);
            throw e;
        }
        finally {
            close(em);
        }
        return result;
    }
}
