package ru.lomakosv.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Converter {

    public static String encodeToBase64(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        return Base64.getEncoder().encodeToString(bytes);
    }
}
