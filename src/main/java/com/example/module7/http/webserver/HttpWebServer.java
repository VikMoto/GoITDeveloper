package com.example.module7.http.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpWebServer {
    private final int port;

    public HttpWebServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        run(10000);
    }
    public static void run(int port){
        try  {
            ServerSocket serverSocket = new ServerSocket(port);
            final ExecutorService executor = Executors.newFixedThreadPool( 10 );

            while (true) {
                System.out.println("wait for connection...");
                Socket connection = serverSocket.accept();

                executor.submit( () -> {
                    try {
                        handleConnection(connection);
                    } catch (IOException e) {
                        throw new RuntimeException( e );
                    } catch (InterruptedException e) {
                        throw new RuntimeException( e );
                    }
                } );



            }
        } catch (IOException e) {
            throw new RuntimeException( e );
        }

    }

    private static void handleConnection(Socket connection) throws IOException, InterruptedException {
        try {
            System.out.println("Client connected! ");
            InputStream is = connection.getInputStream();

//                String requestText = readData(is);
            HttpRequest request = HttpRequest.of(readData(is));
//                System.out.println("*****");
//                System.out.println("requestText = " + requestText);
//                System.out.println("*****");
//                System.out.println("readData = " + request);
//                System.out.println("*****");


            HttpResponse response = new HttpResponse();
            response.setStatusCode(200);
            response.setStatusText("Ok");


//                response.setBody("Hello World!");
            System.out.println("request.getPath() = " + request.getPath());
            try {
                String content = HtmlPages.getFileByPath(request.getPath());
                response.setBody(content);

            }catch (FileNotFoundException e) {
                response.setStatusCode(404);
                response.setStatusText("NOT FOUND");
                response.setBody("Not found!");
            }

            String responseText = response.toString();
//                System.out.println("responseText = " + responseText);
            byte[] bytes = responseText.getBytes(StandardCharsets.UTF_8);

            connection.getOutputStream().write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private static String readData(InputStream is) throws InterruptedException, IOException {
        Thread.sleep(1000);
        byte[] buffer = new byte[1024 * 20];

        int length = 0;
        while (is.available() > 0) {
            int read = is.read(buffer,length,is.available());
            length +=read;
           Thread.sleep(1000);
        }
        return new String(buffer,0,length);
    }

    private static void processSocket(Socket socket){
        try(DataInputStream dIs = new DataInputStream(socket.getInputStream());
            DataOutputStream dOs = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("dIs.available() = " + dIs.available());
            System.out.println("dIs.readUTF() = " + dIs.readUTF());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
