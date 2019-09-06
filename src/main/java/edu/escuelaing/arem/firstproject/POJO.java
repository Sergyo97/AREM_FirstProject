package edu.escuelaing.arem.firstproject;

/**
 *
 * @author Sergio Ruiz
 */
public class POJO {

    /**
     * Returns String from a function that has a label
     * @return String
     */
    @Web("test")
    public static String test() {
        return "Class test";
    }

    /**
     * Returns String from a function that has a label
     * @param n1 Number to add
     * @param n2 Number to add
     * @return HTML with result of operation
     */
    @Web("add")
    public static String sumar(String n1, String n2) {
        return "<html>" + "<head/>" + "<body>" + "<h2> Answer is: "
                + Integer.toString(Integer.parseInt(n1) + Integer.parseInt(n2)) + "</h2>" + "</body>" + "</html>";
    }

}