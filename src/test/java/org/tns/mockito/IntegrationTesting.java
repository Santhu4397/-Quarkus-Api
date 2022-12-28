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
//    @Test
//    public void saveUser(){
//        JsonObject user = Json
//                .createObjectBuilder()
//                .add("name","Gus")
//                .add("email","Gus@gmail.com")
//                .add("productName","Mobile")
//                .add("userStartDate","10-14-2022")
//                .add("userEndDate","10-14-2024")
//                .build();
//        given()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(user.toString())
//                .when()
//                .post("http://localhost:8080/training/user")
//                .then()
//                .assertThat()
//                .statusCode(Response.Status.OK.getStatusCode())
//                .body("status", equalTo("SUCCESS"))
//                .body("responseCode", equalTo("201"))
//                .body("responseDescription", equalTo("User Data Inserted"));
//
//
//    }


}
