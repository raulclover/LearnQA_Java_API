package homework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortPhraseTest {
    @Test
    public void shortPhraseTest() {
        String hello = "Hello world";
        assertTrue(hello.length()>=15, "Длина строки меньше 15, длина строки - " + hello.length());
    }
}
