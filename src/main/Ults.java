package main;

import rendering.RenderEngine;

import javax.swing.*;

/**
 * Collection of random methods in a class because java is stupid, at least static is a thing
 */
public class Ults {
    /**
     * Sleeps current thread
     * @param millisecoonds
     */
    public static void sleep(long millisecoonds) {
        try {
            Thread.sleep(millisecoonds);
        } catch (InterruptedException e) {
            String msg = String.format("Thread interrupted: %s", e.getMessage());
        }
    }

    /**
     * It does what it says it does...
     * @param num
     * @param digits
     * @return
     */
    public static double round(double num, int digits){
        double scale = Math.pow(10, digits);
        return Math.round(scale * num) / scale;
    }

    /**
     * Calculates a^2 + b^2 = c^2 returning c
     * @param a
     * @param b
     * @return
     */
    public static double hypot(double a, double b) {
        return Math.sqrt(a*a + b*b);
    }
}
