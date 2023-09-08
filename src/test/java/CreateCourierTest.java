import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import my.hikandgo.AppliedMethods;
import my.hikandgo.jsonObject.RequestCreate;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("CreateCourier")
    public void createCourierTest() {

        RequestCreate user = new RequestCreate("test_hikand213", "pass", "pass");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("ok", equalTo(true)).and().statusCode(201);

        AppliedMethods.deleteCourier(user);

    }

    @DisplayName("CreateDuplicateCourier")
    @Test
    public void duplicateCreationCourierTest() {
        RequestCreate user = new RequestCreate("test_hikand213", "pass", "pass");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body(notNullValue()).and().statusCode(409);

        AppliedMethods.deleteCourier(user);
    }

    @DisplayName("LoginDuplicate")
    @Test
    public void duplicateCreationLoginTest() {
        RequestCreate user = new RequestCreate("test_hikand213", "pass", "pass");
        RequestCreate userLoginDuplicate = new RequestCreate("test_hikand213", "123", "Fast1");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(userLoginDuplicate)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body(notNullValue()).and().statusCode(409);

        AppliedMethods.deleteCourier(user);
    }

    @DisplayName("CreateCourierWithoutLogin")
    @Test
    public void createCourierWithoutRequiredLoginTest() {

        RequestCreate user = new RequestCreate("", "pass", "firstName");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @DisplayName("CreateCourierWithoutPass")
    @Test
    public void createCourierWithoutRequiredPasswordTest() {

        RequestCreate user = new RequestCreate("loginHikand123", "", "firstName");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @DisplayName("CreateCourierWithoutName")
    @Test
    public void createCourierWithoutRequiredFirstNameTest() {

        RequestCreate user = new RequestCreate("loginHikand123", "password", "");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);

    }


    @DisplayName("CreateCourierWithLoginNull")
    @Test
    public void createCourierLoginNullTest() {

        RequestCreate user = new RequestCreate(null, "password", "");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @DisplayName("CreateCourierWithPassNull")
    @Test
    public void createCourierPassNullTest() {

        RequestCreate user = new RequestCreate("test15231", null, "");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")).and().statusCode(400);

    }

    @DisplayName("CreateCourierWithNameNull")
    @Test
    public void createCourierFirstNameNullTest() {

        RequestCreate user = new RequestCreate("test15231", "pass", null);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")).and().statusCode(409);

    }


}
