package org.example;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;


@Epic("Users API")
public class ApiTest {
    private static final String BASE_URL =  "https://jsonplaceholder.typicode.com";


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Feature("Get user")
    @Description("GET /users/1 returns 200 and correct name")
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
    @Feature("Get user")
    @Description("GET /users/9999 returns 404 for unknown user")
    public void getUnknownUserReturns404(){
        given()
        .when()
                .get("/users/9999")
        .then()
                .statusCode(404);
    }

    @Test
    @Feature("Create post")
    @Description("POST /posts with JSON string returns 201 and same title")
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
    @Feature("Create post")
    @Description("POST /posts with POJO returns 201 and same title")
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
    @Feature("Delete post")
    @Description("DELETE /posts/1 returns 200")
    public void deletePost1(){
        given()
        .when()
                .delete("/posts/1")
        .then()
                .statusCode(200);

    }

    @Test
    @Feature("Get posts")
    @Description("GET /posts?userId=1 returns 200 and posts of user 1")
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
    @Feature("Update")
    @Description("PUT /posts/1 returns 200 and updated title")
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
    @Feature("Get user")
    @Description("GET /users returns 200 and 10 users")
    public void getSize10(){
        given()
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                .body("size()", equalTo(10));

    }
}
