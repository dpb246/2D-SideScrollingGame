package main;

import physics.PhysicsWorld;
import rendering.Level_Tile;
import rendering.RenderEngine;

import java.nio.file.NoSuchFileException;

public class Level_Manager {
    private int current_level_number = 0;
    private String base_path = "./resources/Levels/level";
    private Level_Tile current_level_tile = null;
    private Player p;
    private boolean player_won = false;
    private int number_of_levels;
    public Level_Manager(Player p, int number_of_levels) {
        this.p = p;
        this.number_of_levels = number_of_levels;
        this.load();
    }
    private void load() {
        PhysicsWorld.getInstance().removeAll(); //Reset Physics
        RenderEngine.getInstance().removeAllRenderables(); //Reset drawing
        //RenderEngine.getInstance().getCurrentcam().getPos().x = 0;

        if (current_level_number >= number_of_levels) {
            player_won = true;
            return;
        }
        current_level_tile = new Level_Tile(0, 0, base_path + String.valueOf(current_level_number) + ".txt"); //Load next level
        RenderEngine.getInstance().setLevel(current_level_tile);

        current_level_number++;
        p.loading_level(current_level_tile.getPlayer_spawn()); //tell player
    }
    public void changeLevel(int number) {
        this.current_level_number = number;
        this.load();
    }
    public boolean getPlayerWon() {
        return player_won;
    }
    private void restart() {
        p.restart(current_level_tile.getPlayer_spawn());
        current_level_tile.reset();
        //RenderEngine.getInstance().getCurrentcam().reset();
    }
    public void update() {
        if (p.getWant_next_level()) {
            this.load();
        }
        if (p.getWantRestart()) {
            restart();
        }
        if (p.getPos().x < -20 || p.getPos().y < -30 || p.getPos().x > current_level_tile.getMaxX()*2 || p.getPos().y > RenderEngine.Wanted_HEIGHT*2) {
            restart();
        }
        current_level_tile.update();
    }
}
