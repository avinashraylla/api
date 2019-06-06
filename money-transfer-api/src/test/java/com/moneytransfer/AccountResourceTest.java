package com.moneytransfer;

import com.moneytransfer.common.dto.AccountDTO;
import com.moneytransfer.common.dto.AccountRecipientDTO;
import com.moneytransfer.common.dto.AccountTransactionDTO;
import com.moneytransfer.common.dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class AccountResourceTest {

    private static Server jettyServer;
    private static org.h2.tools.Server h2Server;

    @BeforeClass
    public static void setUp() throws Exception {
        h2Server = App.startH2Db();
        jettyServer = App.initializeJettyServer();
        jettyServer.start();
        App.initializeDatabase();
        RestAssured.baseURI="http://localhost";
        RestAssured.port = 9090;
    }

    @AfterClass
    public static void tearDown() throws Exception {
        jettyServer.stop();
        h2Server.stop();
    }


    @Test
    public void testGetAccounts() {
        Response response = RestAssured.get("/accounts/").andReturn();
        Assert.assertEquals(response.getStatusCode(),200);
    }


    @Test
    public void crateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(BigDecimal.valueOf(500234));
        accountDTO.setUserId(1L);
        accountDTO.setAccountType(1);
        accountDTO.setCurrency("USD");
        AccountRecipientDTO recipientDTO = new AccountRecipientDTO();
        recipientDTO.setName("bill pay");
        recipientDTO.setCurrency("USD");
        recipientDTO.setRecipientAccount(3L);
        accountDTO.setAccountRecipients(Arrays.asList(recipientDTO));

        Response response =  given()
                .contentType(ContentType.JSON)
                .body(accountDTO)
                .post("/accounts/")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());

    }

    @Test
    public void testGetAccountById() {
        Response response = get("/accounts/2/").andReturn();
        Assert.assertEquals(200,response.getStatusCode());
        response.then().body("accountNumber",equalTo(2));
    }


    @Test
    public void testUpdateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(2L);
        accountDTO.setBalance(BigDecimal.valueOf(600235));
        accountDTO.setUserId(1L);
        accountDTO.setAccountType(2);
        accountDTO.setCurrency("USD");

        Response response =  given()
                .contentType(ContentType.JSON)
                .body(accountDTO)
                .put("/accounts/")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertEquals(BigDecimal.valueOf(600235),response.getBody().as(AccountDTO.class).getBalance());

    }

    @Test
    public void addRecipient() {

        AccountRecipientDTO recipientDTO = new AccountRecipientDTO();
        recipientDTO.setRecipientAccount(3L);
        recipientDTO.setName("bills");
        recipientDTO.setAccountNumber(2L);
        recipientDTO.setCurrency("USD");
        Response response =  given()
                .contentType(ContentType.JSON)
                .body(recipientDTO)
                .post("/accounts/2/recipients")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());

    }


    @Test
    public void removeRecipient() {

        AccountRecipientDTO recipientDTO = new AccountRecipientDTO();
        recipientDTO.setRecipientAccount(3L);
        recipientDTO.setName("bills");
        recipientDTO.setAccountNumber(2L);
        recipientDTO.setCurrency("USD");
        Response response =  given()
                .contentType(ContentType.JSON)
                .body(recipientDTO)
                .delete("/accounts/2/recipients")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());

    }

    @Test
    public void transferBalance() {
        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setSourceAccount(2L);
        transactionDTO.setDestinationAccount(3L);
        transactionDTO.setAmount(BigDecimal.valueOf(2000));
        transactionDTO.setComments("bill pay");
        Response response =  given()
                .contentType(ContentType.JSON)
                .body(transactionDTO)
                .post("/accounts/2/transfers")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());
    }

    @Test
    public void getAllTransfers() {
        Response response =  get("/accounts/2/transfers").andReturn();
        Assert.assertEquals(200,response.getStatusCode());
    }

    @Test
    public void deposit() {
       Response response =  given()
                .contentType(ContentType.JSON)
                .body(BigDecimal.valueOf(5000))
                .post("/accounts/2/deposit")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());
    }


    @Test
    public void withdraw() {

        Response response =  given()
                .contentType(ContentType.JSON)
                .body(BigDecimal.valueOf(5000))
                .post("/accounts/2/withdraw")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());
    }
}
