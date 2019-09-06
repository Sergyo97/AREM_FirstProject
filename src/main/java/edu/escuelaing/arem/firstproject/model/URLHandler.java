package edu.escuelaing.arem.firstproject.model;

import java.lang.reflect.Method;

public class UrlHandler implements Handler {

    private Method method;

    public UrlHandler(Method m) {
        this.method = m;
    }

    public String process() {
        try {
            return (String) method.invoke(null, null);
        } catch (Exception e) {
            // TODO: handler exception
            e.printStackTrace();
            return e.toString();
        }
    }

	public String[] processParams() {
		// TODO Auto-generated method stub
		return null;
	}
}