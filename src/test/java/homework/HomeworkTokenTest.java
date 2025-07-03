package homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HomeworkTokenTest {
    @Test
    public void restAssuredTokenTest() throws InterruptedException {
        String url = "https://playground.learnqa.ru/ajax/api/longtime_job";

        JsonPath response = RestAssured
                .given()
                .get(url)
                .jsonPath();

        response.prettyPrint();
        String token = response.get("token");

        Map<String, String> body = new HashMap<>();
        body.put("token", token);

        Response responseWithError = RestAssured
                .given()
                .queryParams(body)
                .when()
                .get(url)
                .andReturn();
        responseWithError.prettyPrint();

        System.out.println("Ожидание - 17 секунд");

        Thread.sleep(17000);

        Response responseWithoutError = RestAssured
                .given()
                .queryParams(body)
                .when()
                .get(url)
                .andReturn();
        responseWithoutError.prettyPrint();
    }
}
