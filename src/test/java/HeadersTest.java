import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HeadersTest {
    @Test
    public void restAssuredHeadersTest(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get(" https://playground.learnqa.ru/api/get_303")
                .andReturn();
        response.prettyPrint();
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}
