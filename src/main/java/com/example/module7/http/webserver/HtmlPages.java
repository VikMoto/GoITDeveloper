package com.example.module7.http.webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringJoiner;

public class HtmlPages {
    //index.html
    //test.html

    public static String getFileByPath(String path) throws FileNotFoundException {
        if (path.equals("/")){
            return readHtmlFile("index.html");
        }else {
            return readHtmlFile(path.substring(1));
        }
    }
    public static String readHtmlFile(String fileName) throws FileNotFoundException {
        File file = new File("web/" + fileName);


        StringJoiner result = new StringJoiner("\n");

        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()){
                result.add(scanner.nextLine());
            }
        }

        return result.toString();
    }
}
