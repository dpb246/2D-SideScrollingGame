package main;

import rendering.RenderEngine;

import javax.swing.*;

public class Ults {
    public static void sleep(long millisecoonds) {
        try {
            Thread.sleep(millisecoonds);
        } catch (InterruptedException e) {
            String msg = String.format("Thread interrupted: %s", e.getMessage());
        }
    }
    public static double round(double num, int digits){
        double scale = Math.pow(10, digits);
        return Math.round(scale * num) / scale;
    }
    public static double hypot(double a, double b) {
        return Math.sqrt(a*a + b*b);
    }
}
