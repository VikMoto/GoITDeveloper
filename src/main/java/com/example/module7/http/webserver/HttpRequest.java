package com.example.module7.http.webserver;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

@Data
public class HttpRequest {
    private Method method;
    private String path;
    private String protocol;
    private Map<String,String> headers = new LinkedHashMap<>();
    private String body;
    public enum Method{
        GET,
        POST
    }

    public static HttpRequest of(String text){


        HttpRequest request = new HttpRequest();

        String[] lines = text.split("\n");

        System.out.println("***********************************************");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replace("\r", "");
//            System.out.println(lines[i]);
        }

        //parse start line
        String startLine = lines[0];
        String[] startLineParts = startLine.split(" ");

//        System.out.println("startLineParts*****************************");
//
//        for (int i = 0; i < startLineParts.length ; i++) {
//
//            System.out.println(startLineParts[i]);
//        }
//        System.out.println("****************************************");

        // check if the method value is valid
        Method method = null;
        try {
            method = Method.valueOf(startLineParts[0]);
            System.out.println(method);
        }catch (IllegalArgumentException e){
            System.err.println("Invalid HTTP method: " + startLineParts[0]);
        }

        request.setMethod(method);

//        System.out.println("***********************************");
//        System.out.println("startLineParts.length = " + startLineParts.length);


        String pathStart = startLineParts[1];
//        System.out.println(pathStart);

        request.setPath(startLineParts[1]);
        request.setProtocol(startLineParts[2]);
//
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
//            System.out.println("line = [" + line + "]");

            if (line.equals("") ) {
                //read body
                StringJoiner body = new StringJoiner("\n");
                for (int j = i ; j < lines.length; j++) {
                    body.add(lines[j]);
                }
            }else {
                String[] keyValue = line.split(": ");
                request.getHeaders().put(
                        keyValue[0].strip(),
                        keyValue[1].strip()
                );

            }

        }
        return request;
    }
}
