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
import java.util.Set;

import javax.imageio.ImageIO;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import edu.escuelaing.arem.firstproject.model.Handler;
import edu.escuelaing.arem.firstproject.model.UrlHandlers;

/**
 *
 * @author Sergio Ruiz
 */
public class AppServer {

    public static HashMap<String, Handler> hs = new HashMap<String, Handler>();

    /**
     * Method that listens to the port and calls the request handler
     * 
     * @throws IOException
     */
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
                    manageRequest(request, out, clientSocket.getOutputStream());
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

    /**
     * Initializes the method containing the annotations
     */
    public static void initialize() {
        bind();
    }

    /**
     * Loads methods that have annotations and adds them to a hashmap
     * 
     * @param classpath path of the class that where it is instance
     */
    public static void bind() {
        try {
            Reflections reflections = new Reflections("edu.escuelaing.arem.firstproject", new SubTypesScanner(false));
            Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);

            for (Class cls : allClasses) {
                for (Method m : cls.getMethods()) {
                    if (m.isAnnotationPresent(Web.class)) {
                        Handler hd = new UrlHandlers(m);
                        hs.put("/apps/" + m.getAnnotation(Web.class).value(), hd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Manages the arrival of the petition to determine which path to choose.
     * 
     * @param request      Petition Path String
     * @param out          Printwriter containing client socket
     * @param outputStream Socket to handle
     * @throws IOException
     */
    private static void manageRequest(String request, PrintWriter out, OutputStream outputStream) throws IOException {
        String[] parts = request.trim().split("\n");
        String route = parts[0].split(" ")[1];
        String[] elements = route.split("/");

        try {
            if (elements.length <= 1) {
                readHTML("index.html", outputStream, out);
            } else {
                String element = elements[elements.length - 1];

                if (element.endsWith(".jpg")) {
                    readImage(element, outputStream, out);
                } else if (element.endsWith(".html")) {
                    readHTML(element, outputStream, out);
                } else if (route.contains("/apps/")) {
                    readApps(elements, outputStream, out);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    /**
     * Method that handles the reading and sending of the elements necessary for
     * access to web applications.
     * 
     * @param elements     list containing the path to the app you want to access
     * @param outputStream Socket to handle
     * @param out          Printwriter containing client socket
     * @throws IOException
     */
    private static void readApps(String[] elements, OutputStream outputStream, PrintWriter out) throws IOException {
        if (elements.length == 3) {
            String type = elements[1];
            String method = elements[2];
            String key = "/" + type + "/" + method;

            if (!elements[2].contains("?")) {
                out.print("HTTP/1.1 200 OK \r\n");
                out.print("Content-Type: text/html \r\n");
                out.print("\r\n");
                out.print(hs.get(key).process());
            } else {
                key = key.split("\\?")[0];
                String paramString = elements[2].split("\\?")[1];
                String[] paramValues = paramString.split("&");
                Object[] params = new Object[paramValues.length];
                for (int i = 0; i < paramValues.length; i++) {
                    params[i] = paramValues[i].split("=")[1];
                }
                out.print("HTTP/1.1 200 OK \r\n");
                out.print("Content-Type: text/html \r\n");
                out.print("\r\n");
                out.print(hs.get(key).processParams(params));

            }
        }
    }

    /**
     * 
     * @param element      Piece of petition path to identify file type
     * @param outputStream Socket to handle
     * @param out          Printwriter containing client socket
     * @throws IOException
     */
    private static void readHTML(String element, OutputStream outputStream, PrintWriter out) throws IOException {
        BufferedReader bf = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/resources/htmls/" + element));
        out.print("HTTP/1.1 200 OK \r\n");
        out.print("Content-Type: text/html \r\n");
        out.print("\r\n");
        String line;
        while ((line = bf.readLine()) != null) {
            out.print(line);
        }
        bf.close();
    }

    /**
     * 
     * @param element      Piece of petition path to identify file type
     * @param outputStream Socket to handle
     * @param out          Printwriter containing client socket
     * @throws IOException
     */
    private static void readImage(String element, OutputStream outputStream, PrintWriter out) throws IOException {
        BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "/resources/images/" + element));
        ByteArrayOutputStream bytesArray = new ByteArrayOutputStream();
        DataOutputStream writeImage = new DataOutputStream(outputStream);
        ImageIO.write(image, "jpg", bytesArray);
        writeImage.writeBytes("HTTP/1.1 200 OK \r\n");
        writeImage.writeBytes("Content-Type: image/jpg \r\n");
        writeImage.writeBytes("Content-Length: " + bytesArray.toByteArray().length + "\r\n");
        writeImage.writeBytes("\r\n");
        writeImage.write(bytesArray.toByteArray());
        System.out.println(System.getProperty("user.dir") + "/resources/images/" + element);
    }

    /**
     * Port through which you are going to listen
     * 
     * @return port
     */

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}