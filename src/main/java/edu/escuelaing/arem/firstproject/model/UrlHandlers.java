package edu.escuelaing.arem.firstproject.model;

import java.lang.reflect.Method;

/**
 *
 * @author Sergio Ruiz
 */
public class UrlHandlers implements Handler {

    private Method method;

    /**
     * 
     * @param m
     */
    public UrlHandlers(Method m) {
        this.method = m;
    }

    /**
     *
     * @return string of an invoked method
     */
    public String process() {
        try {
            return (String) method.invoke(null, null);
        } catch (Exception e) {
            // TODO: handler exception
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     *
     * @param params array of objects
     * @return string of an invoked method
     */
    public String processParams(Object[] params) {
        try {
            return (String) method.invoke(null, params);
        } catch (Exception e) {
            // TODO: handler exception
            e.printStackTrace();
            return e.toString();
        }
	}
}