package com.moneytransfer;

import com.moneytransfer.common.dto.UserDTO;
import com.moneytransfer.common.jpa.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.eclipse.jetty.server.Server;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class UserResourceTest {

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

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetUsers() {
        Response response = RestAssured.get("/users/").andReturn();
        Assert.assertEquals(response.getStatusCode(),200);
    }


    @Test
    public void crateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("adasf");
        userDTO.setLastName("asdsf");
        userDTO.setFirstName("adfasdf");

        Response response =  given()
                .contentType(ContentType.JSON)
                .body(userDTO)
                .post("/users/")
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(200,response.getStatusCode());
        response.then().body("userId",notNullValue());

    }

    @Test
    public void testGetUserById() {
        Response response = get("/users/2").andReturn();
        Assert.assertEquals(200,response.getStatusCode());
        response.then().body("userId",equalTo(2));
    }

    @Test
    public void testGetUserByWrongId() {
        Response response = get("/users/100").andReturn();
        Assert.assertEquals(404,response.getStatusCode());
    }

    @Test
    public void testUserRemove() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("adasf");
        userDTO.setLastName("asdsf");
        userDTO.setFirstName("adfasdf");
        Response response =   RestAssured.delete("/users/1").andReturn();
        Assert.assertEquals(200,response.getStatusCode());

    }
}
