package edu.escuelaing.arem.firstproject;

public class POJO {

    @Web("test")
    public static String test() {
        return "Class test";
    }

    @Web("add")
    public static String sumar(String n1, String n2) {
        return "<html>" + "<head/>" + "<body>" + "<h2> Answer is: "
                + Integer.toString(Integer.parseInt(n1) + Integer.parseInt(n2)) + "</h2>" + "</body>" + "</html>";
    }

}