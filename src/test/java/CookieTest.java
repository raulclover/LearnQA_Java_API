import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CookieTest {
    @Test
    public void restAssuredCookieTest(){
        Map<String, String> data = new HashMap<>();
        data.put("login","secret_login1");
        data.put("password", "secret_pass");

        Response responseForGet = RestAssured
                .given()
                .body(data)
                .when()
                .post(" https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        String responseCookie = responseForGet.getCookie("auth_cookie");

        Map<String, String> cookies = new HashMap<>();
        if(responseCookie!=null){
            cookies.put("auth_cookie", responseCookie);
        }else{
            System.out.println("Нет куки для авторизации");
        }

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .post(" https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();

        responseForCheck.print();
    }
}
