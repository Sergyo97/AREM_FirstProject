package edu.escuelaing.arem.firstproject;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import edu.escuelaing.arem.firstproject.model.Handler;
import edu.escuelaing.arem.firstproject.model.UrlHandlers;
import net.sf.image4j.codec.ico.ICODecoder;
import net.sf.image4j.codec.ico.ICOEncoder;

public class AppServer {

    public static HashMap<String, Handler> hs = new HashMap<String, Handler>();

    static void listener() throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4567.");
            System.exit(1);
        }
        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Ready to listen ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String request = "";
            String inputLine;

            try {
                while (!(inputLine = in.readLine()).equals("")) {
                    request += inputLine + "\n";
                    readRequest(request, out, clientSocket.getOutputStream());
                    if (in.ready())
                        break;
                }
            } catch (NullPointerException e) {
                // TODO: handle exception
                out.print("HTTP/1.1 404 not Found \r\n");
            }

            out.close();
            in.close();
        }

    }

    public static void initialize() {
        String st = "edu.escuelaing.arem.firstproject.POJO";
        bind(st);
    }

    public static void bind(String classpath) {
        try {
            Class cls = Class.forName(classpath);
            for (Method m : cls.getMethods()) {
                if (m.isAnnotationPresent(Web.class)) {
                    Handler hd = new UrlHandlers(m);
                    hs.put("/apps/" + m.getAnnotation(Web.class).value(), hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readRequest(String request, PrintWriter out, OutputStream outputStream) throws IOException {
        String[] parts = request.trim().split("\n");
        String route = parts[0].split(" ")[1];
        String[] elements = route.split("/");
        String element = elements[elements.length - 1];

        try {
            if (element.endsWith(".jpg")) {
                readImage(element, outputStream, out);
            } else if (element.endsWith(".html")) {
                readHTML(element, outputStream, out);
            } else if (route.contains("/apps/")) {
                if (elements.length == 3) {
                    String type = elements[1];
                    String method = elements[2];
                    String key = "/" + type + "/" + method;
                    out.print("HTTP/1.1 200 OK \r\n");
                    out.print("Content-Type: text/html \r\n");
                    out.print("\r\n");
                    out.print(hs.get(key).process());
                }
            } else if (element.contains("favicon.ico")) {
                manageFavicon(out);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private static void readHTML(String element, OutputStream outputStream, PrintWriter out) throws IOException {
        BufferedReader bf = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "\\resources\\htmls\\" + element));
        out.print("HTTP/1.1 200 OK \r\n");
        out.print("Content-Type: text/html \r\n");
        out.print("\r\n");
        String line;
        while ((line = bf.readLine()) != null) {
            out.print(line);
        }
        bf.close();
    }

    private static void readImage(String element, OutputStream outputStream, PrintWriter out) throws IOException {
        BufferedImage image = ImageIO
                .read(new File(System.getProperty("user.dir") + "\\resources\\images\\" + element));
        ByteArrayOutputStream bytesArray = new ByteArrayOutputStream();
        DataOutputStream writeImage = new DataOutputStream(outputStream);
        ImageIO.write(image, "jpg", bytesArray);
        writeImage.writeBytes("HTTP/1.1 200 OK \r\n");
        writeImage.writeBytes("Content-Type: image/jpg \r\n");
        writeImage.writeBytes("Content-Length: " + bytesArray.toByteArray().length + "\r\n");
        writeImage.writeBytes("\r\n");
        writeImage.write(bytesArray.toByteArray());
        System.out.println(System.getProperty("user.dir") + "\\resources\\images\\" + element);
    }

    private static void manageFavicon(PrintWriter out) throws IOException {
        List<BufferedImage> images = ICODecoder.read(new File(System.getProperty("user.dir") + "images.ico"));
        out.println("HTTP/1.1 200 OK\r");
        out.println("Content-Type: image/vnd.microsoft.icon\r");
        out.println("\r");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ICOEncoder.write(images.get(0), baos);
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}