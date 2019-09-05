package edu.escuelaing.arem.firstproject.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class URLHandler implements Handler {

    private Method method;

    public URLHandler(Method m){
        this.method = m;
    }

    public String process(){
        try {
            return (String) method.invoke(null, null);
        } catch (Exception e) {
            //TODO: handler exception
            e.printStackTrace();
            return e.toString();
        }
    }

    public Class<? extends Annotation> annotationType() {
        return null;
    }

}