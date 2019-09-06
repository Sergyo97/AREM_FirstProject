package edu.escuelaing.arem.firstproject;

import java.io.IOException;

/**
 * @author Sergio Ruiz
 *
 */
public class Controller {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        AppServer.initialize();
        AppServer.listener();
    }
}
