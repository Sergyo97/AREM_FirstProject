package edu.escuelaing.arem.firstproject;

/**
 *
 * @author Sergio Ruiz
 */
public class POJO1 {

    /**
     * Returns String from a function that has a label
     * @return String
     */
    @Web("test")
    public static String test() {
        return "Class test 1";
    }
}