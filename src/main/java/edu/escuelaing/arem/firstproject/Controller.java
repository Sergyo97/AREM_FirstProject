package edu.escuelaing.arem.firstproject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService eService = Executors.newCachedThreadPool();
        eService.submit(new AppServer());
    }
}
