package org.tns.mockito;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTest
public class IntegrationTesting {
    @Test
    public void userGet(){
            given() .pathParam("username", "Jas")
                .when().get("http://localhost:8080/training/getalluser/{username}")
                .then().statusCode(200)
                .body("status", equalTo("ok"))
                .body("responseCode", equalTo("200"))
                .body("responseDescription", equalTo("User Data fetched"))
                .body("data.email", equalTo("Jas@gmail.com"))
                .body("data.name", equalTo("Jas"));
    }


}
