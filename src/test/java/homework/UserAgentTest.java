package homework;

import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentTest {
    final String a = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    final String b = "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1";
    final String c = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    final String d = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0";
    final String e = "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";

    @ParameterizedTest
    @ValueSource(strings = {a,b,c,d,e,})

    public void testUserAgent(String userAgent) {
        Response response = given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .andReturn();

        String actualPlatform = response.jsonPath().getString("platform");
        String actualBrowser = response.jsonPath().getString("browser");
        String actualDevice = response.jsonPath().getString("device");

        String expectedPlatform = "";
        String expectedBrowser = "";
        String expectedDevice = "";

        if (userAgent.equals(a)) {
            expectedPlatform = "Mobile";
            expectedBrowser = "No";
            expectedDevice = "Android";
        } else if (userAgent.equals(b)) {
            expectedPlatform = "Mobile";
            expectedBrowser = "Chrome";
            expectedDevice = "iOS";
        } else if (userAgent.equals(c)) {
            expectedPlatform = "Googlebot";
            expectedBrowser = "Unknown";
            expectedDevice = "Unknown";
        } else if (userAgent.equals(d)) {
            expectedPlatform = "Web";
            expectedBrowser = "Chrome";
            expectedDevice = "No";
        } else if (userAgent.equals(e)) {
        expectedPlatform = "Mobile";
        expectedBrowser = "No";
        expectedDevice = "iPhone";
        }else {
            throw new IllegalArgumentException("Передан некорректный аргумент");
        }

        assertEquals(expectedPlatform, actualPlatform, "Несовпадает платформа: " + userAgent);
        assertEquals(expectedBrowser, actualBrowser, "Несовпадает браузер: " + userAgent);
        assertEquals(expectedDevice, actualDevice, "Несовпадает устройство: " + userAgent);
    }
}
