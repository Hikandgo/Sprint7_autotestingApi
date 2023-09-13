import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import my.hikandgo.AppliedMethods;
import my.hikandgo.jsonObject.RequestCreate;
import my.hikandgo.jsonObject.RequestCreateOrder;
import my.hikandgo.jsonObject.RequestLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;



@RunWith(Enclosed.class)
public class CreateOrderTest {

    // Проверяем создание заказов и проверяем корзину у того-же юзера
    public static int userId;

    public static class CheckOrdersCourierTest {

        @Test
        public void checkOrderTest() {
            Response response = given()
                    .get("/api/v1/courier/" + CreateOrderTest.userId + "/ordersCount");

            response.then().assertThat().body(notNullValue()).and().statusCode(404);
        }
    }

    @RequiredArgsConstructor
    @RunWith(Parameterized.class)
    public static class CreateOrdersTest {

        private final RequestCreateOrder order;
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

            Response res = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(new RequestLogin(user.getLogin(), user.getPassword()))
                    .when()
                    .post("/api/v1/courier/login");

            userId = res.jsonPath().getInt("id");
        }

        @After
        public void deleteAllCourier() {
            AppliedMethods.deleteCourier(user);
        }

        @Parameterized.Parameters
        public static Object[][] getOrder() {
            return new Object[][]{
                    {new RequestCreateOrder("firstName", "lastBName", "adress", "metro", "1231231", 5, "2023-05-05", "comm", new String[]{"BLACK"})},
                    {new RequestCreateOrder("firstName", "lastBName", "adress", "metro", "1231231", 5, "2023-05-05", "comm", new String[]{"GREY"})},
                    {new RequestCreateOrder("firstName", "lastBName", "adress", "metro", "1231231", 5, "2023-05-05", "comm", new String[]{"BLACK", "GREY"})},
                    {new RequestCreateOrder("firstName", "lastBName", "adress", "metro", "1231231", 5, "2023-05-05", "comm", null)}
            };
        }

        @Test
        public void creteOrderTest() {

            Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(order)
                    .when()
                    .post("/api/v1/orders");

            response.then().assertThat().body("track", notNullValue()).and().statusCode(201);

        }

    }

}
