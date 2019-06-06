package com.moneytransfer.services;

import com.moneytransfer.common.UserDAO;
import com.moneytransfer.common.UserService;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.UserDTO;
import static com.moneytransfer.common.utils.EntityDtoConverter.*;

import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.User;
import com.moneytransfer.common.utils.EntityDtoConverter;
import com.moneytransfer.dao.UserDAOImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public UserDTO createUser(UserDTO user) throws Exception {
        return convertToUserDTO(userDAO.createUser(convertDtoToUser(user)));
    }

    @Override
    public UserDTO updateUser(UserDTO user) throws Exception {
        return convertToUserDTO(userDAO.createUser(convertDtoToUser(user)));
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<User> users = userDAO.getAllUsers();
        List<UserDTO> result = null;
        if(users!=null && users.size()>0) {
            result = users.stream().map(EntityDtoConverter::convertToUserDTO).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public UserDTO getUserById(Long userId) throws Exception {
        return convertToUserDTO(userDAO.getUserById(userId));
    }

    @Override
    public void deleteUser(Long userId) throws Exception {
        userDAO.deleteUser(userId);
    }

    @Override
    public List<AccountDTO> getUserAccounts(Long userId) throws Exception {
        List<Account> accounts= userDAO.getUserAccounts(userId);
        List<AccountDTO> result = null;
        if(accounts!=null && accounts.size()>0) {
            result = accounts.stream().map(EntityDtoConverter::convertToAccountDTO).collect(Collectors.toList());
        }
        return result;
    }
}
