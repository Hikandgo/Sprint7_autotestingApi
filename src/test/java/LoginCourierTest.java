import io.restassured.RestAssured;
import io.restassured.response.Response;
import my.hikandgo.AppliedMethods;
import my.hikandgo.jsonObject.RequestCreate;
import my.hikandgo.jsonObject.RequestLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    private RequestCreate user;

    @Before
    public void setUp() {

        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";

        this.user = new RequestCreate("logCourierHTest", "12345", "Firstname");

        try {
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(user)
                    .when()
                    .post("/api/v1/courier");
        } catch (Error e) {
            System.out.println(e.getMessage());
        }

    }


    @Test
    public void loginCourierTest() {

        Response res = given()
                .header("Content-type", "application/json")
                .and()
                .body(this.user)
                .when()
                .post("/api/v1/courier/login");

        res.then().assertThat().body("id", notNullValue()).and().statusCode(200);
    }

    @Test
    public void loginCourierWithoutLoginTest() {

        // Проверка на логин курьера без передачи всех обязательных полей (только ЛОГИН)
        Response res = given()
                .header("Content-type", "application/json")
                .and()
                .body(new RequestLogin(this.user.getLogin(), ""))
                .when()
                .post("/api/v1/courier/login");

        res.then().assertThat().body(notNullValue()).and().statusCode(400);
    }

    @Test
    public void loginCourierWithoutPassTest() {

        // Проверка на логин курьера без передачи всех обязательных полей (только ПАРОЛЬ)

        Response res = given()
                .header("Content-type", "application/json")
                .and()
                .body(new RequestLogin(null, this.user.getPassword()))
                .when()
                .post("/api/v1/courier/login");

        res.then().assertThat().body(notNullValue()).and().statusCode(400);
    }

    @Test
    public void loginNoNCourierTest() {

        // Проверка на логин несуществующей учетной записи
        Response res = given()
                .header("Content-type", "application/json")
                .and()
                .body(new RequestLogin("nonexistuserhikand_12345", "pass"))
                .when()
                .post("/api/v1/courier/login");

        res.then().assertThat().body(notNullValue()).and().statusCode(404);
    }

    @After
    public void deleteAllCourier() {
        AppliedMethods.deleteCourier(this.user);
    }
}
