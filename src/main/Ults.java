package main;

import javax.swing.*;

public class Ults {
    public static void sleep(long millisecoonds) {
        try {
            Thread.sleep(millisecoonds);
        } catch (InterruptedException e) {
            String msg = String.format("Thread interrupted: %s", e.getMessage());
        }
    }
}
