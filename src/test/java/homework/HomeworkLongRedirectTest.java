package homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HomeworkLongRedirectTest {
    @Test
    public void restAssuredRedirectTest() {
        Response response;
        String url = "https://playground.learnqa.ru/api/long_redirect";

        do {
            response = RestAssured
                    .given()
                    .redirects().follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            if (response.getStatusCode() != 200) {
                url = response.getHeader("Location");
                System.out.println("Редирект на: " + url);
            }
        } while (response.getStatusCode() != 200);
        System.out.println("Код 200");
    }
}