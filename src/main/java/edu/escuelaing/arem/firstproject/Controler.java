package edu.escuelaing.arem.firstproject;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Controler {
    public static void main(String[] args) throws IOException
    {
        AppServer.initialize();
        AppServer.listener();
    }
}
