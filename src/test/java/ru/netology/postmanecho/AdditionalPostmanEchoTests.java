package ru.netology.postmanecho;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AdditionalPostmanEchoTests {

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
    public void shouldReturnBodyJSON() {

        Map<String, String> dataForRequest = new HashMap<>();

        dataForRequest.put("name", "Timur");
        dataForRequest.put("age", "36");

        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(dataForRequest)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data.name", equalTo("Timur"))
                .body("data.age", equalTo("36"));
    }

    @Test
    public void shouldReturnMultipleObjects() {

        List<Map<String, String>> dataForRequestBody = new ArrayList<>();
        Map<String, String> object1 = new HashMap<>();
        object1.put("name", "Ivan");
        object1.put("age", "30");

        Map<String, String> object2 = new HashMap<>();
        object2.put("name", "Maria");
        object2.put("age", "20");

        dataForRequestBody.add(object1);
        dataForRequestBody.add(object2);

        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(dataForRequestBody)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("json.size()", equalTo(2))
                .body("json[0].name", equalTo("Ivan"))
                .body("json[1].name", equalTo("Maria"));
    }
}
