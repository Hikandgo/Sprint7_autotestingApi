package my.hikandgo;


import io.restassured.RestAssured;
import my.hikandgo.jsonObject.RequestCreate;
import my.hikandgo.jsonObject.RequestLogin;

import static io.restassured.RestAssured.given;


public class AppliedMethods {
    public static void deleteCourier(RequestCreate user) {

        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

        RequestLogin userLogin = new RequestLogin(user.getLogin(), user.getPassword());

        int userId = given()
                .header("Content-type", "application/json")
                .and()
                .body(userLogin)
                .when()
                .post("/api/v1/courier/login")
                .jsonPath()
                .getInt("id");


        given().header("Content-type", "application/json").delete("/api/v1/courier/" + userId);
    }

}
