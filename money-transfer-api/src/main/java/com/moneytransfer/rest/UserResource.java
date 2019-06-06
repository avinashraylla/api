package com.moneytransfer.rest;

import com.moneytransfer.common.AccountService;
import com.moneytransfer.common.UserService;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.UserDTO;
import com.moneytransfer.services.AccountsServiceImpl;
import com.moneytransfer.services.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {
    private UserService userService = new UserServiceImpl();
    private AccountService accountService = new AccountsServiceImpl();
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserDTO> users=null;
        Response response = null;
        try {
            users = userService.getAllUsers();
            response = Response.status(200).entity(users).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserDTO user) {
        Response response = null;
        try {
            UserDTO result = userService.createUser(user);
            response = Response.status(200).entity(result).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }
        return response;
    }

    @DELETE()
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long userId) {
        Response response = null;
        try {
            userService.deleteUser(userId);
            response = Response.status(200).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

        }
        return response;
    }

    @GET()
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") Long userId) {
        UserDTO user=null;
        Response response = null;
        try {
            user = userService.getUserById(userId);
            if(user!=null) {
                response = Response.status(200).entity(user).build();
            }
            else {
                response = Response.status(404).entity("No User Found").build();
            }

        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return response;
    }

    @GET()
    @Path("/{userId}/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@PathParam("userId") Long userId) {
        List<AccountDTO> accounts=null;
        Response response = null;
        try {
            accounts = userService.getUserAccounts(userId);
            if(accounts!=null) {
                response = Response.status(200).entity(accounts).build();
            }
            else {
                response = Response.status(404).entity("No User Accounts Found").build();
            }

        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @GET()
    @Path("/{userId}/accounts/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccountById(@PathParam("userId") Long userId,@PathParam("accountId") Long accountId) {
         AccountDTO accounts=null;
        Response response = null;
        try {
            accounts = accountService.getAccountById(accountId);
            if(accounts!=null) {
                response = Response.status(200).entity(accounts).build();
            }
            else {
                response = Response.status(404).entity("No User Accounts Found").build();
            }

        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return response;
    }

}
