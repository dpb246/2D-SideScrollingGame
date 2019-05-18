package input;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * Keyboard containing an array of all current key states
 * @author Devin Blankespoor
 * @version February 2019
 */

/**
 * Stores the possible states of a key
 */
enum keyState {
    JUST_PRESSED, DOWN, UP, JUST_RELEASED
}

public class Keyboard extends JComponent {
    private keyState[] keys = new keyState[256];
    private static Keyboard k = null;
    private Keyboard () {
        //Initialize keyDown array and key bindings
        for (int i = 0; i < 256; i++) {
            keys[i] = keyState.UP;
            addKeyPressed(i, new updateKey(i, keyState.JUST_PRESSED));
            addKeyReleased(i, new updateKey(i, keyState.JUST_RELEASED));
        }
    }
    /**
     * Trys to set the key to the new state
     * @param key KeyEvent.VK_ Code for the key
     * @param state desired keyState for the key
     * @return true if state updated
     */
    private boolean requestStateUpdate(int key, keyState state) {
        if ((keys[key] == keyState.UP || keys[key] == keyState.JUST_RELEASED) && state == keyState.JUST_PRESSED) {
            keys[key] = state;
            return true;
        }
        if ((keys[key] == keyState.DOWN || keys[key] == keyState.JUST_PRESSED) && state == keyState.JUST_RELEASED) {
            keys[key] = state;
            return true;
        }
        return false;
    }
    /**
     * Updates all states from:
     * keyState.JUST_PRESSED to keyState.DOWN
     * keyState.JUST_RELEASED to keyState.UP
     */
    public void updateStates() {
        for (int i = 0; i < 256; i++) {
            if (keys[i] == keyState.JUST_PRESSED) {
                keys[i] = keyState.DOWN;
            } else if (keys[i] == keyState.JUST_RELEASED) {
                keys[i] = keyState.UP;
            }
        }
    }
    /**
     * Getter for static instance
     * @return Keyboard instance
     */
    public static Keyboard getInstance() {
        if (k == null) k = new Keyboard();
        return k;
    }
    /**
     * Returns true if key is down
     * @param key KeyEvent.VK_ Code for the key
     * @return boolean true if key is currently down
     */
    public boolean isDown(int key) {
        if (keys[key] == keyState.DOWN || keys[key] == keyState.JUST_PRESSED) return true;
        return false;
    }
    /**
     * Returns true if key is just pressed
     * @param key KeyEvent.VK_ Code for the key
     * @return boolean true if key was just pressed
     */
    public boolean justPressed(int key) {
        if (keys[key] == keyState.JUST_PRESSED) return true;
        return false;
    }
    /**
     * Returns true if key was just released
     * @param key KeyEvent.VK_ Code for the key
     * @return boolean true if key was just released
     */
    public boolean justReleased(int key) {
        if (keys[key] == keyState.JUST_RELEASED) return true;
        return false;
    }
    /**
     * Returns true if key is UP
     * @param key KeyEvent.VK_ Code for the key
     * @return boolean true if key is UP
     */
    public boolean isUp(int key) {
        if (keys[key] == keyState.UP || keys[key] == keyState.JUST_RELEASED) return true;
        return false;
    }
    /**
     * Adds key press binding for given key to given event
     * @param key KeyEvent.VK_ Code for the key
     * @param act Action to be executed on key press event
     */
    private void addKeyPressed(int key, AbstractAction act) {
        this.getInputMap().put(KeyStroke.getKeyStroke(key, 0, false), key+"Pressed");
        this.getActionMap().put(key+"Pressed", act);
    }
    /**
     * Adds key release binding for given key to given event
     * @param key KeyEvent.VK_ Code for the key
     * @param act Action to be executed on key press event
     */
    private void addKeyReleased(int key, AbstractAction act) {
        this.getInputMap().put(KeyStroke.getKeyStroke(key, 0, true), key+"Released");
        this.getActionMap().put(key+"Released", act);
    }
    /**
     * Action to update key to a given state
     */
    private class updateKey extends AbstractAction {
        private int key;
        private keyState state;
        /**
         * @param key KeyEvent.VK_ Code for the key
         * @param state State key should be set too
         */
        public updateKey(int key, keyState state) {
            this.key = key;
            this.state = state;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            requestStateUpdate(this.key, this.state);
        }
    }
}
