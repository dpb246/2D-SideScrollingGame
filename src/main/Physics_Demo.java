package main;
import physics.*;

public class Physics_Demo {

    public static void main(String[] args) {
        PhysicsEngine world = new PhysicsEngine(9.8);
        Box test = new Box(0.0,0.0,2.0,2.0,10.0, false, 10.0, 10.0);
        world.add(test);
        int simCycles = 15;
        for (int cycle = 0; cycle < simCycles; cycle++){
            world.simulate();
        }
    }

}
