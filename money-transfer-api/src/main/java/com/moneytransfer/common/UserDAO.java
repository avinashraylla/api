package com.moneytransfer.common;

import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.User;

import java.util.List;

public interface UserDAO {

    public User createUser(User user) throws Exception;
    public User updateUser(User user) throws Exception;
    public List<User> getAllUsers() throws Exception;
    public User getUserById(Long userId) throws Exception;
    public void deleteUser(Long userId) throws Exception;
    public List<Account> getUserAccounts(Long userId) throws Exception;

}
