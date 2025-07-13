package homework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieAssertTest {
    @Test
    public void cookieAssertTest(){
        Map<String, String> cookie = new HashMap<>();
        cookie.put("HomeWork", "hw_value");

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        assertEquals(response.getCookies(), cookie, "Куки из респонса не совпадает с ожидаемой");
    }
}
