import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SimpleGetTest {
    @Test
    public void restAssuredGetTest(){
        Map<String, String> params = new HashMap<>();
        params.put("name", "John");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get(" https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = response.get("answer");
        System.out.print(answer);
    }
}
