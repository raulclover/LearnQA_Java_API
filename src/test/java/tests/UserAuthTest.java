package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lib.Assertions;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("Authorisation cases")
@Feature("Auth")
public class UserAuthTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
    public void loginUser(){
        Map<String, String> authData= new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePost("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = getCookie(responseGetAuth, "auth_sid");
        this.header = getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = getIntFromJson(responseGetAuth, "user_id");
    }
    @Description("test1")
    @DisplayName("Test1")
    @Test
    public void testAuthUser(){
        Response responseCheckAuth = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/auth", this.header, this.cookie);

        Assertions.assertJsonByName(responseCheckAuth, "user_id", userIdOnAuth);
    }

    @Description("test2")
    @DisplayName("Test2")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "header"})
    public void testNegativeAuthUser(String condition){
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");
        if (condition.equals("cookie")){
            spec.cookie("auth_sid", this.cookie);
        } else if(condition.equals("header")){
            spec.header("x-csrf-token", this.header);
        }else {
            throw new IllegalArgumentException("Condition value is known " + condition);
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    }
}
