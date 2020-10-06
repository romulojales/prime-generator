package com.romulojales.integrationtests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PrimeIT {

    @BeforeAll
    static void init() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    void should_get_the_list_of_primes() {
        given()
                .when()
                .get("/primes/17")
                .then()
                .body(equalTo("2,3,5,7,11,13,17."))
                .statusCode(200);

    }

    @Test
    void should_return_404_when_the_number_is_empty() {
        given()
                .when()
                .get("/primes")
                .then()
                .statusCode(404);

        given()
                .when()
                .get("/primes/")
                .then()
                .statusCode(404);
    }

    @Test
    void should_return_400_when_the_number_is_not_a_number() {
        given()
                .when()
                .get("/primes/a")
                .then()
                .statusCode(400);

        given()
                .when()
                .get("/primes/d123")
                .then()
                .statusCode(400);
    }
}
