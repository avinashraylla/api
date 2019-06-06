package com.moneytransfer.common;

import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO createUser(UserDTO user) throws Exception;
    public UserDTO updateUser(UserDTO user) throws Exception;
    public List<UserDTO> getAllUsers() throws Exception;
    public UserDTO getUserById(Long userId) throws Exception;
    public void deleteUser(Long userId) throws Exception;
    public List<AccountDTO> getUserAccounts(Long userId) throws Exception;

}
