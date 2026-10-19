package org.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {
    private static final String BASE_URL =  "https://jsonplaceholder.typicode.com";

    @Test
    public void getUserReturns200AndCorrectName(){
        given()
                .baseUri(BASE_URL)
        .when()
                .get("/users/1")
        .then()
                .statusCode(200)
                .body("name",equalTo("Leanne Graham"))
                .body("address.city",notNullValue());
    }

    @Test
    public void getUnknownUserReturns404(){
        given()
                .baseUri(BASE_URL)
        .when()
                .get("/users/9999")
        .then()
                .statusCode(404);
    }

    @Test
    public void postEqualsTitle(){
        given()
                .baseUri(BASE_URL)
                .header("Content-Type","application/json")
                .body("{ \"title\":\"test\",\"body\":\"hello\",\"userId\": 1 }")
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title", equalTo("test"));
    }

    @Test
    public void getSize10(){
        given()
                .baseUri(BASE_URL)
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .body("size()", equalTo(10));

    }
}
