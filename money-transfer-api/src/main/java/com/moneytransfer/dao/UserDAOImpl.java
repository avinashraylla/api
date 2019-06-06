package com.moneytransfer.dao;

import com.moneytransfer.common.UserDAO;
import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.User;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

import static com.moneytransfer.common.utils.EntityManagerUtil.close;
import static com.moneytransfer.common.utils.EntityManagerUtil.getEntityManager;
import static com.moneytransfer.common.utils.EntityManagerUtil.rollbackAndClose;

public class UserDAOImpl implements UserDAO {


    @Override
    public User createUser(User user) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
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

        return user;
    }

    @Override
    public User updateUser(User user) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user1 = (User)em.createQuery("from User where userId=:userId").setParameter("userId",user.getUserId()).getSingleResult();
            em.lock(user1, LockModeType.PESSIMISTIC_WRITE);
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setAddress(user.getAddress());
            em.merge(user1);
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
        return user;
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        EntityManager em = null;

        List<User> users  = null;
        try {
            em = getEntityManager();
            users = em.createQuery("from User u").getResultList();
            em.close();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) throws Exception {
        EntityManager em = null;
        User user1 = null;
        try {
            em = getEntityManager();
            user1 = em.find(User.class,userId);
            close(em);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return user1;
    }

    @Override
    public void deleteUser(Long userId) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user1 = (User)em.createQuery("from User where userId=:userId").setParameter("userId",userId).getSingleResult();
            em.remove(user1);
            em.getTransaction().commit();
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
    public List<Account> getUserAccounts(Long userId) throws Exception {
        EntityManager em = null;
        List<Account> accounts = null;
        try {
            em = getEntityManager();
            accounts = em.createQuery("from Account where user.userId=:userId").setParameter("userId",userId).getResultList();
            close(em);

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            close(em);
        }
        return accounts;
    }
}
