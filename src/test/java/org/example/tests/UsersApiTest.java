package org.example.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.example.base.BaseTest;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.example.base.Endpoints.USERS;
import static org.example.base.Endpoints.USER_BY_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("Users API")
@Feature("Users")
public class UsersApiTest extends BaseTest {

    @Test
    @Description("GET /users/1 returns 200 and correct name")
    void getUserReturns200AndCorrectName() {
        given().spec(requestSpec)
                .when()
                .get(USER_BY_ID, 1)
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .body("address.city", notNullValue());
    }

    @Test
    @Description("GET /users/9999 returns 404 for unknown user")
    void getUnknownUserReturns404() {
        given().spec(requestSpec)
                .when()
                .get(USER_BY_ID, 9999)
                .then()
                .statusCode(404);
    }

    @Test
    @Description("GET /users returns 200 and 10 users")
    void getAllUsersReturns10() {
        given().spec(requestSpec)
                .when()
                .get(USERS)
                .then()
                .statusCode(200)
                .body("size()", equalTo(10));
    }

    @ParameterizedTest(name = "GET /users/{0} returns user with the same id")
    @ValueSource(ints = {1, 2, 5, 10})
    @Description("Для каждого id в ответе приходит пользователь с этим же id")
    void getUserByIdReturnsSameId(int id) {
        given().spec(requestSpec)
                .when()
                .get(USER_BY_ID, id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    @Test
    @Description("Ответ GET /users/1 соответствует JSON-схеме")
    void userMatchesJsonSchema() {
        given().spec(requestSpec)
                .when()
                .get(USER_BY_ID, 1)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test
    @Description("Ответ десериализуется в User, email содержит @")
    void userDeserializesToPojo() {
        User user = given().spec(requestSpec)
                .when()
                .get(USER_BY_ID, 1)
                .then()
                .statusCode(200)
                .extract().as(User.class);

        assertThat(user.getId(), equalTo(1));
        assertThat(user.getUsername(), equalTo("Bret"));
        assertThat(user.getEmail(), containsString("@"));
    }
}
