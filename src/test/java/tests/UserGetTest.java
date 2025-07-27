package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    @Test
    public void testGetUserDataNotAuth(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        System.out.println(response.asString());
        Assertions.assertJSONHasField(response, "username");
        Assertions.assertJSONHasNotField(response, "email");
        Assertions.assertJSONHasNotField(response, "firstName");
        Assertions.assertJSONHasNotField(response, "lastName");
    }

    @Test
    public void testGetUserDataAuthAsSameUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response response = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();


        String header = this.getHeader(response, "x-csrf-token");
        String cookie = this.getCookie(response, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        String[] expectedFields = {"username", "firstName", "lastName", "email"};

        Assertions.assertJSONHasFields(responseUserData, expectedFields);
    }

    @Test
    @DisplayName("Попытка получить данные другого пользователя")
    public void testGetUserDataAuthAsAnotherUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response loginResponse = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String token = this.getHeader(loginResponse, "x-csrf-token");
        String cookie = this.getCookie(loginResponse, "auth_sid");

        int otherUserId = 1;
        Response userDataResponse = RestAssured
                .given()
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/" + otherUserId)
                .andReturn();

        System.out.println("Response body: " + userDataResponse.asString());

        Assertions.assertOnlyUsernameVisible(userDataResponse);
    }
}
