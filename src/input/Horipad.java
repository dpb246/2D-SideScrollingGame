package input;
import net.java.games.input.*;
import java.util.Date;

public class Horipad {

    private Controller controller = null;
    private boolean[] isPressed = new boolean[15 + 8];
    private boolean[] wasPressed = new boolean[15 + 8];
    private float[] joysticks = new float[4];

    private static String[] buttonName = new String[] {"Y", "B", "A", "X", "L", "R", "ZL", "ZR", "-", "+", "LStick", "RStick", "HOME", "CAPTURE", "DPAD LEFT", "LEFT", "RIGHT", "UP", "DOWN", "CLEFT", "CRIGHT", "CUP", "CDOWN"};

    private float deadZone;


    /**
     * Create a controller for the controller so you can control things with the controller controller
     * @param stickSensitivity a float from 0 to 1.0 determining how far the stick has to move before
     *                         it registers a directional press. A value of 0.5 is recommended.
     */
    public Horipad(float stickSensitivity, boolean findDefault) {

        if (findDefault) {
            // find the controllers connected
            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

            // set to null if no controllers are found
            if (controllers.length == 0){
                controller = null;
                return;
            }

            // use the last controller listed as the input device
            for (Controller c : controllers) {
                if (c.getType() == Controller.Type.GAMEPAD) {
                    controller = c;
                }
            }
        }

        if (stickSensitivity < 0){stickSensitivity = 0.0f;}
        deadZone = stickSensitivity * 10.0f;

    }

    /**
     * @return an array of all buttons currently being held
     */
    public String[] getPressedButtons(){

        // find how many are currently pressed
        int numPressed = 0;
        for (boolean b : isPressed){
            if (b){
                numPressed++;
            }
        }

        // store the names of each pressed button
        String[] result = new String[numPressed];
        numPressed = 0;
        for (int i = 0; i<isPressed.length; i++){
            if (isPressed[i]){
                result[numPressed] = buttonName[i];
                numPressed++;
            }
        }

        return result;

    }

    public String getButton(){
        String[] pressedButtons = getPressedButtons();
        while (pressedButtons.length < 1){
            pressedButtons = getPressedButtons();
        }
        return pressedButtons[0];
    }

    /**
     * Waits for input from a controller and sets that as the main controller to scan for inputs
     * @param timeout time before it stops searching for inputs
     */
    public void findController(int timeout){

        System.out.println("Press a button on the desired controller");

        Controller savedController = controller;
        controller = null;
        Date startTime = new Date();

        isPressed = new boolean[isPressed.length];

        while (controller == null){

            if (savedController != null && new Date().getTime() > startTime.getTime() + timeout){
                System.out.println("Timeout Exceeded: no controller detected");
                controller = savedController;
                return;
            }

            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

            if (controllers.length == 0){
                System.out.println("No controller connected");
                break;
            }

            for (Controller c : controllers){

                if (c.getType() == Controller.Type.GAMEPAD) {

                    pollInputs(c);

                    for (boolean buttonPressed : isPressed){
                        if (buttonPressed){
                            controller = c;
                            System.out.println("Controller detected");
                            return;
                        }
                    }
                }

            }

        }
    }

    private StringBuffer  lastPoll = new StringBuffer();

    /**
     * Main function for checking inputs
     * @param displayInputs whether the buttons will be displayed to the console or not
     */
    public void input(boolean displayInputs){

        if (controller == null){return;}

        StringBuffer inputs = pollInputs(controller);

        // Detect change in buttons
        if (displayInputs && !inputs.toString().equals(lastPoll.toString())) {
            showButtons();
            showJoystick();
        }

        lastPoll = inputs;

    }

    /**
     * Poll the controller for input.
     * This modifies the boolean array of buttons and the float array of joystick inputs.
     * @return The current state of all inputs as a string buffer
     */
    private StringBuffer pollInputs(Controller c){

        c.poll();

        Component[] components = c.getComponents();
        StringBuffer buffer = new StringBuffer();

        int js_idx = 0;
        int btn_idx = 0;

        for (int i = 0; i < components.length; i++) {

            wasPressed[i] = isPressed[i];

            if (i > 0) {buffer.append(", ");}
            buffer.append(components[i].getName());

            if (components[i].isAnalog()) {

                // Set the analog inputs from -10 to 10
                float f = Math.round(components[i].getPollData()*1000)/100;
                if (Math.abs(f) < 0.1){ // hard coded deadZone
                    f = 0.0f;
                }
                buffer.append(f);
                if (js_idx < joysticks.length) {
                    joysticks[js_idx] = f;
                    js_idx++;
                }

            } else {
                if (components[i].getPollData() == 1.0f) {
                    buffer.append("1");
                    isPressed[btn_idx] = true;
                } else {
                    buffer.append("0");
                    isPressed[btn_idx] = false;
                }
                btn_idx++;
            }
        }

        setDirectionalButtons();

        return buffer;
    }

    /**
     * Sets the states of directional "buttons" based on analog input
     */
    private void setDirectionalButtons() {

        // loop for each analog stick
        for (int i = 0; i < 2; i++) {

            String[] StickDirection = joystickDir(joysticks[3 - 2 * i], joysticks[2 - 2 * i]);

            isPressed[15 + 4 * i] = false;
            isPressed[16 + 4 * i] = false;
            isPressed[17 + 4 * i] = false;
            isPressed[18 + 4 * i] = false;

            if (StickDirection[0].equals("LEFT")) {
                isPressed[15 + 4 * i] = true;
            } else if (StickDirection[0].equals("RIGHT")) {
                isPressed[16 + 4 * i] = true;
            }
            if (StickDirection[1].equals("UP")) {
                isPressed[17 + 4 * i] = true;
            } else if (StickDirection[1].equals("DOWN")) {
                isPressed[18 + 4 * i] = true;
            }

        }

    }

    /**
     * Determines which directions are being held on the analog stick
     * @param x horizontal value
     * @param y vertical value
     * @return {"Horizontal", "Vertical"}
     */
    private String[] joystickDir(float x, float y){

        String[] result = {"",""};

        if (Math.abs(x) > deadZone){

            if (x > 0){
                result[0] = "RIGHT";
            } else { // x < 0
                result[0] = "LEFT";
            }

        }

        if (Math.abs(y) > deadZone) {

            if (y < 0){ // -y is up for some reason
                result[1] =  "UP";
            } else { // y > 0
                result[1] =  "DOWN";
            }

        }

        return result;

    }


    /**
     * Displays the held buttons to the console
     */
    private void showButtons(){
        StringBuffer s = new StringBuffer();
        for (int i = 0; i<15; i++){
            if (isPressed[i]) {
                s.append("| "); s.append(buttonName[i]); s.append(" |");
            }
        }
        if (s.length() != 0){
            System.out.println(s);
        }
    }


    /**
     * Displays the joystick inputs to the console
     */
    private void showJoystick(){
        String s = "";
        if (joysticks[2] != 0.0f){s+="| Y-Axis: " + joysticks[2] + " |";}
        if (joysticks[3] != 0.0f){s+="| X-Axis: " + joysticks[3] + " |";}
        if (joysticks[0] != 0.0f){s+="| CStick-Y: " + joysticks[0] + " |";}
        if (joysticks[1] != 0.0f){s+="| CStick-X: " + joysticks[1] + " |";}
        if (!s.equals("")){
            System.out.println(s);
        }
    }


    /**
     * Get a button index from its name
     * @param s name of the button
     * @return index
     */
    private int buttonIndex(String s){
        for (int i=0; i<buttonName.length; i++){
            if (buttonName[i].equals(s)) {
                return i;
            }
        }
        return -1; //button not found
    }


    /**
     * Check if a button's state changed from being released to being pressed
     * @param button the buttons name
     * @return whether it's the first frame of a press or not
     */
    public boolean justPressed(String button){
        int idx = buttonIndex(button);
        return idx != -1 && !wasPressed[idx] && isPressed[idx];
    }


    /**
     *
     * @param button the buttons name
     * @return If the button is currently pressed down
     */
    public boolean isDown(String button){
        int idx = buttonIndex(button);
        return idx != -1 && isPressed[idx];
    }


    /**
     * Check if a button's state changed from being pressed to being released
     * @param button the buttons name
     * @return whether it's the first frame of a release or not
     */
    public boolean justReleased(String button){
        int idx = buttonIndex(button);
        return idx != -1 && wasPressed[idx] && !isPressed[idx];
    }


    /**
     *
     * @param button the buttons name
     * @return if the button is currently not being held
     */
    public boolean isUp(String button){
        int idx = buttonIndex(button);
        return idx != -1 && !isPressed[idx];
    }


    /**
     * Get the current position of the left analog stick
     * @return {x, y}
     */
    public float[] getLeftStick(){
        return new float[] {joysticks[3], joysticks[2]};
    }


    /**
     * Get the current position of the right analog stick
     * @return {x, y}
     */
    public float[] getRightStick(){
        return new float[] {joysticks[1], joysticks[0]};
    }

}