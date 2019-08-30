package edu.escuelaing.arem.firstproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {

    public static void listener() throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(45000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();

    }

    public static void initialize() {
        try {
            Class cls = Class.forName("edu.escuelaing.arem.firstproject.Test");
            Method met = cls.getDeclaredMethod("test", null);
            System.out.format("invoking %s.met()%n", cls.getName());
            System.out.println(met.invoke(null, null));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}