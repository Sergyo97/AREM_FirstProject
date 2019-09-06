package edu.escuelaing.arem.firstproject.model;

/**
 *
 * @author Sergio Ruiz
 */
public interface Handler {

    /**
     * Invoke a method that has a label
     * @return a string containing the response of the method invoked
     */
    public String process();

    /**
     * Invoke a method that has a label
     * @param params delivered parameters
     * @return a string containing the response of the method invoked
     */
    public String processParams(Object[] params);

}