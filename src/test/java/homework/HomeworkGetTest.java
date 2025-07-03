package homework;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class HomeworkGetTest {
    @Test
    public void restAssuredGetTest(){
        String url = "https://playground.learnqa.ru/api/get_json_homework";
        JsonPath response = RestAssured
                .given()
                .get(url)
                .jsonPath();

        String message = response.getString("messages[1].message");
        System.out.println(message);
    }
}
