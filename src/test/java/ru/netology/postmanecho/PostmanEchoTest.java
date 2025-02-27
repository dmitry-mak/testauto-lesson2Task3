package ru.netology.postmanecho;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    @Test
    public void shouldReturnSomeData() {

        given()
                .baseUri("https://postman-echo.com")
                .body("Some data")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", equalTo("Some data"));
    }

    @Test
    public void headersTest() {

        given()
                .baseUri("https://postman-echo.com")
                .header("Custom-header", "Custom value")
                .body("Some data")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("headers.custom-header", equalTo("Custom value"));
    }

    @Test
    public void shouldReturnBodyJSON(){

        HashMap<String, String> dataForRequest = new HashMap<>();

        dataForRequest.put("name", "Dmitry");
        dataForRequest.put("age","40");

        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(dataForRequest)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data.name", equalTo("Dmitry"))
                .body("data.age", equalTo("40"));
    }
}
