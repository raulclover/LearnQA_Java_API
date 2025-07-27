package lib;

import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataGenerator {
    @Step("Сгенерировать рандомный email")
    public static String getRandomEmail() {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timeStamp + "@example.com";
    }

    @Step("Сгенерировать рандомный email с заданной длинной имени")
    public static String getRandomEmail(int length) {
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder sb = new StringBuilder(length);
            Random random = new Random();

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(alphabet.length());
                sb.append(alphabet.charAt(index));
            }

            return sb.toString()+"@example.com";
    }

    public static Map<String, String> getRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");
        return data;
    }

    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = DataGenerator.getRegistrationData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }
}