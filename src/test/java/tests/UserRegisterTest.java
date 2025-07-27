package tests;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;

import java.util.HashMap;
import java.util.Map;

import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testCreateUserWithExistingEmail(){

        String email = "vinkotov@example.com";

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response response = apiCoreRequests.makePost("https://playground.learnqa.ru/api/user/", userData);
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        Assertions.assertResponseTextEquals(response, "Users with email '"+email+"' already exists");
        Assertions.assertResponseCodeEquals(response, 400);
    }
    @Description("Создание пользователя без @ в Email")
    @DisplayName("Test2")
    @Test
    public void testCreateUserWithEmailWithoutAt(){

        String email = "vinkotovexample.com";

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response response = apiCoreRequests.makePost("https://playground.learnqa.ru/api/user/", userData);
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        Assertions.assertResponseTextEquals(response, "Invalid email format");
        Assertions.assertResponseCodeEquals(response, 400);
    }

    @Description("Создание пользователя с коротким Email")
    @DisplayName("Test3")
    @Test
    public void testCreateUserWithShortEmail(){

        String email = DataGenerator.getRandomEmail(1);

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response response = apiCoreRequests.makePost("https://playground.learnqa.ru/api/user/", userData);
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        System.out.println(email);
        Assertions.assertJSONHasField(response, "id");
        Assertions.assertResponseCodeEquals(response, 200);
    }

    @Description("Создание пользователя с длинным Email")
    @DisplayName("Test3")
    @Test
    public void testCreateUserWithLongEmail(){

        String email = DataGenerator.getRandomEmail(251);

        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response response = apiCoreRequests.makePost("https://playground.learnqa.ru/api/user/", userData);
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        Assertions.assertResponseTextEquals(response, "The value of 'email' field is too long");
        Assertions.assertResponseCodeEquals(response, 400);
    }

    @Description("Создание пользователя без указания одного из полей")
    @DisplayName("Test4")
    @ParameterizedTest
    @ValueSource(strings =  {"email", "password", "username", "firstName", "lastName"})
    public void testCreateUserWithoutField(String missingField) {
        Map<String, String> userData = DataGenerator.getRegistrationData();
        userData.remove(missingField);

        Response response = apiCoreRequests.makePost("https://playground.learnqa.ru/api/user/", userData);
        System.out.println("Missing field: " + missingField);
        System.out.println("Response: " + response.asString());
        System.out.println("Status code: " + response.getStatusCode());

        Assertions.assertResponseCodeEquals(response, 400);
        Assertions.assertResponseTextEquals(response, "The following required params are missed: " + missingField);
    }

    @Description("Успешное создание пользователя")
    @DisplayName("Test5")
    @Test
    public void testCreateUserSuccessfully(){
        Map<String,String> userData = DataGenerator.getRegistrationData();
        Response response = apiCoreRequests.makePost("https://playground.learnqa.ru/api/user/", userData);
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
        Assertions.assertResponseCodeEquals(response, 200);
        Assertions.assertJSONHasField(response, "id");

    }
}
