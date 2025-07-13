package homework;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderAssertTest {
    final Date currentTime = new Date();

    @Test
    public void headerAssertTest(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = formatter.format(currentTime);


        Map<String, String> header = new HashMap<>();
        header.put("Date", formattedDate);
        header.put("Content-Type", "application/json");
        header.put("Content-Length", "15");
        header.put("Connection", "keep-alive");
        header.put("Keep-Alive", "timeout=10");
        header.put("Server", "Apache");
        header.put("x-secret-homework-header", "Some secret value");
        header.put("Cache-Control", "max-age=0");
        header.put("Expires", formattedDate);

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers responseHeaders = response.getHeaders();

        Map<String, String> actualHeaders = new HashMap<>();
        for (Header h : responseHeaders) {
            actualHeaders.put(h.getName(), h.getValue());
        }

        assertEquals(header, actualHeaders, "Хэдэры из респонса не совпадает с ожидаемыми");
    }
}
