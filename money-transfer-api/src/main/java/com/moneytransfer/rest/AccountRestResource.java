package com.moneytransfer.rest;

import com.moneytransfer.common.AccountService;
import com.moneytransfer.common.UserService;
import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.AccountRecipientDTO;
import com.moneytransfer.common.dto.AccountTransactionDTO;
import com.moneytransfer.common.dto.UserDTO;
import com.moneytransfer.services.AccountsServiceImpl;
import com.moneytransfer.services.UserServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/accounts")
public class AccountRestResource {
    private UserService userService = new UserServiceImpl();
    private AccountService accountService = new AccountsServiceImpl();

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        List<AccountDTO> accounts=null;
        Response response = null;
        try {
            accounts = accountService.getAllAccounts();
            response = Response.status(200).entity(accounts).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @GET()
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("accountId") Long accountId) {
        AccountDTO account=null;
        Response response = null;
        try {
            account = accountService.getAccountById(accountId);
            response = Response.status(200).entity(account).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @POST()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(AccountDTO accountDTO) {
        Long account=null;
        Response response = null;
        try {
            account = accountService.createAccount(accountDTO);
            response = Response.status(200).entity(account).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @PUT()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(AccountDTO accountDTO) {
        AccountDTO account=null;
        Response response = null;
        try {
            account = accountService.updateAccount(accountDTO);
            response = Response.status(200).entity(account).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }


    @DELETE()
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{accountId}")
    public Response deleteAccount(@PathParam("accountId") Long accountId) {
        Response response = null;
        try {accountService.deleteAccount(accountId);
            response = Response.status(200).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }


    @POST()
    @Path("/{accountId}/recipients")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAccountRecipients(@PathParam("accountId") Long accountId,AccountRecipientDTO recipientDTO) {
        Response response = null;
        try {
             accountService.addRecipient(recipientDTO.getAccountNumber(),recipientDTO.getRecipientAccount(),recipientDTO.getName());
            response = Response.status(200).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @DELETE()
    @Path("/{accountId}/recipients")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteRecipients(@PathParam("accountId") Long accountId,AccountRecipientDTO recipientDTO) {
        Response response = null;
        try {
            accountService.removeRecipient(recipientDTO.getAccountNumber(),recipientDTO.getRecipientAccount());
            response = Response.status(200).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }


    @GET()
    @Path("/{accountId}/recipients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountRecipients(@PathParam("accountId") Long accountId) {
        List<AccountRecipientDTO> recipients=null;
        Response response = null;
        try {
            recipients = accountService.getAccountRecipients(accountId);
            response = Response.status(200).entity(recipients).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }


    @GET()
    @Path("/{accountId}/transfers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountTransactions(@PathParam("accountId") Long accountId) {
        List<AccountTransactionDTO> transactions=null;
        Response response = null;
        try {
            transactions = accountService.getAllTransfers(accountId);
            response = Response.status(200).entity(transactions).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @POST()
    @Path("/{accountId}/transfers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transferMoney(@PathParam("accountId") Long accountId, AccountTransactionDTO transactionDTO) {
        BigDecimal amount = null;
        Response response = null;
        try {
            amount = accountService.transferBalance(transactionDTO);
            response = Response.status(200).entity(amount).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @POST()
    @Path("/{accountId}/deposit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response depositMoney(@PathParam("accountId") Long accountId, BigDecimal deposit) {
        BigDecimal amount = null;
        Response response = null;
        try {
            amount = accountService.deposit(accountId,deposit);
            response = Response.status(200).entity(amount).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }

    @POST()
    @Path("/{accountId}/withdraw")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response withdrawMoney(@PathParam("accountId") Long accountId, BigDecimal withdraw) {
        BigDecimal amount = null;
        Response response = null;
        try {
            amount = accountService.withdraw(accountId,withdraw);
            response = Response.status(200).entity(amount).build();
        }
        catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return response;
    }
}

