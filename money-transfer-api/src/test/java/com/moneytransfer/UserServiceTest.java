package com.moneytransfer;

import com.moneytransfer.common.UserService;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.UserDTO;
import com.moneytransfer.services.UserServiceImpl;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {
    private static org.h2.tools.Server h2Server;
    private static UserService userService;
    private static UserDTO user;

    @BeforeClass
    public static void setUp() throws Exception {
        h2Server = App.startH2Db();
        App.initializeDatabase();
        userService = new UserServiceImpl();
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
    public void createUser() throws Exception {
        UserDTO response = userService.createUser(user);
        Assert.assertNotNull(response);
        user.setUserId(response.getUserId());
    }

    @Test
    public void updateUser() throws Exception {
        user.setFirstName("Jack");
        UserDTO response = userService.updateUser(user);
        Assert.assertNotNull(response);
        Assert.assertEquals(user.getFirstName(), response.getFirstName());
    }

    @Test
    public void getAllUsers() throws Exception {
        List<UserDTO> users = userService.getAllUsers();
        Assert.assertNotNull(users);
    }

    @Test
    public void getUserById() throws Exception {
        UserDTO response = userService.getUserById(1L);
        Assert.assertNotNull(response);
    }

    @Test
    public void deleteUser() throws Exception {
        userService.deleteUser(user.getUserId());
        UserDTO response = userService.getUserById(user.getUserId());
        Assert.assertNull(response);
    }

    @Test
    public void getUserAccounts() throws Exception {
        List<AccountDTO> userAccounts = userService.getUserAccounts(Long.valueOf(1));
        Assert.assertNotNull(userAccounts);
    }


}
