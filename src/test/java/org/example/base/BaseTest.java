package org.example.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

/**
 * Общая настройка для всех API-тестов.
 * Все тестовые классы наследуются от него и не дублируют baseUri / headers.
 */
public abstract class BaseTest {

    // Можно переопределить из консоли: mvn test -DbaseUrl=https://other-api.com
    private static final String BASE_URL = System.getProperty("baseUrl", "https://jsonplaceholder.typicode.com");

    protected static RequestSpecification requestSpec;

    @BeforeAll
    public static void setUpSpec(){
        // Печатает запрос и ответ в консоль только если тест упал
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())  // запросы/ответы попадают в Allure-отчёт
                .build();
    }

}
