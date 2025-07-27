package lib;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class Assertions {
    public static void assertJsonByName (Response response, String name, int expectedValue) {
        response.then().assertThat().body("$", hasKey(name));
        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertJsonByName (Response response, String name, String expectedValue) {
        response.then().assertThat().body("$", hasKey(name));
        String value = response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }


    public static void assertResponseTextEquals (Response response, String expectedAnswer) {
        assertEquals(expectedAnswer, response.asString(), "Response text is not equal to expected");
    }

    public static void assertResponseCodeEquals (Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.statusCode(), "Response status code is not equal to expected");
    }

    public static void assertJSONHasField(Response response, String expectedFieldName) {
        response.then().assertThat().body("$",hasKey(expectedFieldName));
    }
    public static void assertJSONHasFields(Response response, String[] expectedFieldNames) {
        for(String expectedFieldName: expectedFieldNames)
            assertJSONHasField(response, expectedFieldName);
    }

    public static void assertJSONHasNotField(Response response, String unexpectedFieldName) {
        response.then().assertThat().body("$",not(hasKey(unexpectedFieldName)));
    }
    @Step("Проверка отображения только username в response")
    public static void assertOnlyUsernameVisible(Response response) {
        JsonPath jsonPath = response.jsonPath();

        assertTrue(jsonPath.getMap("").containsKey("username"), "Поле 'username' не найдено");

        String[] hiddenFields = {"email", "firstName", "lastName"};
        for (String field : hiddenFields) {
            assertFalse(jsonPath.getMap("").containsKey(field), "Поле '" + field + "' не должно присутствовать");
        }
    }

}
