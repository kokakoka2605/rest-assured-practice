package org.example.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.example.base.BaseTest;
import org.example.models.Post;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.example.base.Endpoints.POSTS;
import static org.example.base.Endpoints.POST_BY_ID;
import static org.example.base.Endpoints.POST_COMMENTS;
import static org.example.base.Endpoints.USER_BY_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Posts API")
public class PostsApiTest extends BaseTest {

    @Test
    @Feature("Get posts")
    @Description("GET /posts?userId=1 returns only posts of user 1")
    void getPostsByUserIdReturnsOnlyThatUser() {
        given().spec(requestSpec)
                .queryParam("userId", 1)
                .when()
                .get(POSTS)
                .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(1)));
    }

    @Test
    @Feature("Get posts")
    @Description("GET /posts/1 matches JSON schema and responds fast")
    void getPostMatchesSchema() {
        given().spec(requestSpec)
                .when()
                .get(POST_BY_ID, 1)
                .then()
                .statusCode(200)
                .time(lessThan(5000L))
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }

    @Test
    @Feature("Get posts")
    @Description("GET /posts/9999 returns 404")
    void getUnknownPostReturns404() {
        given().spec(requestSpec)
                .when()
                .get(POST_BY_ID, 9999)
                .then()
                .statusCode(404);
    }

    @Test
    @Feature("Get posts")
    @Description("GET /posts/1/comments returns 5 comments with valid emails")
    void getPostCommentsReturnsValidEmails() {
        given().spec(requestSpec)
                .when()
                .get(POST_COMMENTS, 1)
                .then()
                .statusCode(200)
                .body("size()", equalTo(5))
                .body("email", everyItem(containsString("@")))
                .body("postId", everyItem(equalTo(1)));
    }

    @Test
    @Feature("Get posts")
    @Description("Автор поста 1 существует как пользователь")
    void postAuthorExistsAsUser() {
        int userId = given().spec(requestSpec)
                .when()
                .get(POST_BY_ID, 1)
                .then()
                .statusCode(200)
                .extract().path("userId");

        given().spec(requestSpec)
                .when()
                .get(USER_BY_ID, userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId));
    }

    @Test
    @Feature("Create post")
    @Description("POST /posts with JSON string returns 201 and same title")
    void createPostWithJsonString() {
        given().spec(requestSpec)
                .body("{ \"title\":\"test\",\"body\":\"hello\",\"userId\": 1 }")
                .when()
                .post(POSTS)
                .then()
                .statusCode(201)
                .body("title", equalTo("test"));
    }

    @Test
    @Feature("Create post")
    @Description("POST /posts with POJO returns 201, same data and generated id")
    void createPostWithPojo() {
        Post request = new Post.Builder()
                .title("test")
                .body("hello")
                .userId(1)
                .build();

        Post response = given().spec(requestSpec)
                .body(request)
                .when()
                .post(POSTS)
                .then()
                .statusCode(201)
                .extract().as(Post.class);

        assertThat(response.getId(), notNullValue());
        assertThat(response.getTitle(), equalTo(request.getTitle()));
        assertThat(response.getBody(), equalTo(request.getBody()));
        assertThat(response.getUserId(), equalTo(request.getUserId()));
    }

    @Test
    @Feature("Update post")
    @Description("PUT /posts/1 returns 200 and updated title")
    void putUpdatesPost() {
        Post post = new Post("updated", "new body", 1);

        given().spec(requestSpec)
                .body(post)
                .when()
                .put(POST_BY_ID, 1)
                .then()
                .statusCode(200)
                .body("title", equalTo("updated"))
                .body("body", equalTo("new body"));
    }

    @Test
    @Feature("Update post")
    @Description("PATCH /posts/1 changes only the title")
    void patchUpdatesOnlyTitle() {
        given().spec(requestSpec)
                .body("{ \"title\": \"patched\" }")
                .when()
                .patch(POST_BY_ID, 1)
                .then()
                .statusCode(200)
                .body("title", equalTo("patched"))
                .body("userId", equalTo(1))
                .body("body", notNullValue());
    }

    @Test
    @Feature("Delete post")
    @Description("DELETE /posts/1 returns 200")
    void deletePost() {
        given().spec(requestSpec)
                .when()
                .delete(POST_BY_ID, 1)
                .then()
                .statusCode(200);
    }
}
