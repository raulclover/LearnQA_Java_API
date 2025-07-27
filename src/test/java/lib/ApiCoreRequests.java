package lib;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Get-запрос авторизации с токеном и куки")
    public Response makeGetRequest(String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Get-запрос авторизации с токеном")
    public Response makeGetRequestWithTokenOnly(String url,String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Get-запрос авторизации с куки")
    public Response makeGetRequestWithCookieOnly(String url,String cookie){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Post-запрос")
    public Response makePost(String url, Map<String, String> data){
        return given()
                .filter(new AllureRestAssured())
                .body(data)
                .post(url)
                .andReturn();
    }

}
