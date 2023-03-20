package com.example.module7.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class HttpBase64 {
    public static void main(String[] args) {
        try (InputStream inputStream = HttpBase64.class.getClassLoader()
                .getResourceAsStream("ckphoto.jpg")) {
            byte[] imageBytes = inputStream.readAllBytes();
            final String string = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println("string = " + string);
            Path.of("GoITDeveloper/src/main/resources", "test.jpg");
            byte[] decode = Base64.getDecoder().decode(string);
            Files.write(Path.of("GoITDeveloper/src/main/resources", "test.jpg"),
                    decode, StandardOpenOption.CREATE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
