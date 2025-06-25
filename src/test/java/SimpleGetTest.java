import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class SimpleGetTest {
    @Test
    public void restAssuredHelloTest(){
        Response response = RestAssured
                .get(" https://playground.learnqa.ru/api/hello")
                .andReturn();
        response.prettyPrint();
    }
}
