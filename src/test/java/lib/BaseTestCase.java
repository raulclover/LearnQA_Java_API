package lib;

import io.restassured.response.Response;
import io.restassured.http.Headers;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestCase {
    protected String getHeader(Response response, String name){
        Headers headers = response.getHeaders();
        assertTrue(headers.hasHeaderWithName(name), "Response has no header with name "+name);
        return headers.getValue(name);
    }

    protected String getCookie(Response response, String name){
        Map<String, String> cookies = response.getCookies();
        assertTrue(cookies.containsKey(name), "Response has no cookie with name "+name);
        return cookies.get(name);
    }

    protected int getIntFromJson (Response response, String name){
        response.then().assertThat().body("$", hasKey(name));
        return response.jsonPath().getInt(name);
    }
}
