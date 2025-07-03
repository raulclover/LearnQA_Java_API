import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class StatusCodeTest {
    @Test
    public void restAssuredStatusTest(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get(" https://playground.learnqa.ru/api/get_303")
                .andReturn();
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }
}
