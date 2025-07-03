package homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HomeworkRedirectTest {
    @Test
    public void restAssuredRedirectTest(){
        String url = "https://playground.learnqa.ru/api/long_redirect";

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get(url)
                .andReturn();

        String responseHeader = response.getHeader("Location");
        System.out.println(responseHeader);
    }
}
