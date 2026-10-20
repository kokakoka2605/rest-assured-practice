package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {
    private static final String BASE_URL =  "https://jsonplaceholder.typicode.com";


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void getUserReturns200AndCorrectName(){
        given()
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
        .when()
                .get("/users/9999")
        .then()
                .statusCode(404);
    }

    @Test
    public void postEqualsTitle(){
        given()
                .header("Content-Type","application/json")
                .body("{ \"title\":\"test\",\"body\":\"hello\",\"userId\": 1 }")
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title", equalTo("test"));
    }

    @Test
    public void createPostWithPojo(){
        Post post = new Post("test","hello",1);

        given()
                .header("Content-Type","application/json")
                .body(post)
        .when()
                .post("/posts")
        .then()
                .statusCode(201)
                .body("title",equalTo("test"));
    }


    @Test
    public void deletePost1(){
        given()
        .when()
                .delete("/posts/1")
        .then()
                .statusCode(200);

    }

    @Test
    public void getPostsByUserIdReturnsOnlyThatUser(){
        given()
                .queryParam("userId", 1)
        .when()
                .get("/posts")

        .then()
                .statusCode(200)
                .body("[0].userId",equalTo(1));

    }


    @Test
    public void updatePost1(){
        Post post = new Post("updated", "new body", 1);

        given()
                .header("Content-Type", "application/json")
                .body(post)
        .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("updated"));

    }
    @Test
    public void getSize10(){
        given()
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .body("size()", equalTo(10));

    }
}
