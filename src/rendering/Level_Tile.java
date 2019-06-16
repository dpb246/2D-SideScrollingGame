package rendering;
import main.Vector2D;
import physics.AABB;
import physics.PhysicsWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides text loading and drawing for a level background/main structure
 */
public class Level_Tile {
    private ArrayList<ArrayList<Image>> images;
    private double x, y;
    private Vector2D player_spawn = null;
    public final static int TILE_SIZE = 80;

    /**
     * Idk why you would want to offset it but just in case
     * @param x
     * @param y
     */
    public Level_Tile(double x, double y) {
        images = new ArrayList<>();
        this.x = x;
        this.y = y;
    }
    /**
     * Idk why you would want to offset it but just in case
     * @param x
     * @param y
     */
    public Level_Tile(double x, double y, String path){
        images = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.load_from_file(path);
    }
    public Level_Tile() {
        this(0, 0);
    }
    /**
     * Wow look file loading for levels
     * @param file_path
     * @return
     */
    public Level_Tile load_from_file(String file_path) {
        Image spike = (new ImageIcon("resources/Pirate Adventure Textures/Other Sprites/spikes.png")).getImage(); //Preload images
        Image floor = (new ImageIcon("resources/Pirate Adventure Textures/wood_floor_large.png")).getImage();
        Image goal = (new ImageIcon("resources/Pirate Adventure Textures/Other Sprites/Chests/chest_gold_l.png")).getImage();
        try {
            if (Files.notExists(Paths.get(file_path))){
                return null;
            }
            ArrayList<String> stream = new ArrayList<>(Files.lines(Paths.get(file_path)).collect(Collectors.toList()));
            Collections.reverse(stream); //Invert file to draw it from the bottom of the screen up, otherwise wouldn't know how long the file is
            int cury = 0;
            PhysicsWorld world = PhysicsWorld.getInstance(); //Little sketch but whatever
            AABB temp;
            for (String line : stream) {
                int curx = 0;
                for (char c : line.toCharArray()) {
                    images.add(new ArrayList<>());
                    switch (c) { //LOL LOL Michael will hate all the highlighted text
                        case '^': //spike resources/Pirate Adventure Textures/Other Sprites/spikes.png
                            images.get(cury).add(spike);
                            temp = world.add(new AABB(new Vector2D(x+curx*TILE_SIZE + TILE_SIZE/2, y+cury*TILE_SIZE + (TILE_SIZE-10)/2), TILE_SIZE, TILE_SIZE-10, 0));
                            temp.bitmask = 1;
                            temp.type = "spike";
                            break;
                        case '=': //ground resources/Pirate Adventure Textures/wood_floor_large.png
                            images.get(cury).add(floor);
                            temp = world.add(new AABB(new Vector2D(x+curx*TILE_SIZE + TILE_SIZE/2, y+cury*TILE_SIZE + TILE_SIZE/2), TILE_SIZE, TILE_SIZE, 0));
                            temp.bitmask = 1;
                            temp.type = "wood";
                            break;
                        case '*': //air
                            //do NOTHING LETS GO PARTY TIME
                            images.get(cury).add(null);
                            break;
                        case 'G': //goal resources/Pirate Adventure Textures/Other Sprites/Chests/chest_gold_l.png
                            images.get(cury).add(goal);
                            temp = world.add(new AABB(new Vector2D(x+curx*TILE_SIZE + TILE_SIZE/2, y+cury*TILE_SIZE + TILE_SIZE/2), TILE_SIZE, TILE_SIZE, 0));
                            temp.bitmask = 1;
                            temp.type = "goal";
                            break;
                        case 'S'://Player Spawn
                            player_spawn = new Vector2D(x+curx*TILE_SIZE + TILE_SIZE/2, y+cury*TILE_SIZE + TILE_SIZE/2);
                            images.get(cury).add(null);
                            break;
                    }
                    curx++;
                }
                cury += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    public Vector2D getPlayer_spawn() {
        return player_spawn.copy();
    }
    /**
     * Surprise Surprise take a guess, it draws everything in this level tile on to the graphics2d
     * @param g2d
     * @param io
     * @param camera_shift
     * @param camera_scale
     */
    public void draw(Graphics2D g2d, ImageObserver io, Vector2D camera_shift, double camera_scale) {
        double curx = 0;
        double cury = 0;
        for (int j = 0; j < images.size(); j++) {
            ArrayList<Image> row = images.get(j);
            int k = (int) Math.max(0, Math.floor((camera_shift.getX()-x)/(TILE_SIZE))); //I wish i figured out how to make this work
            for (k = 0; k < row.size() && (x+curx-camera_shift.getX())*camera_scale < RenderEngine.Wanted_WIDTH+TILE_SIZE*2; k++) {
                //draws until it knows it is 2 tile widths of screen, provides a bit of a buffer but prevents slow down in big levels
                Image i = row.get(k);
                if (i != null) { //Same logic as in renderables
                    AffineTransform at = new AffineTransform();
                    at.translate((x + curx -camera_shift.getX())*camera_scale, (y + cury -camera_shift.getY())*camera_scale);
                    at.rotate(Math.PI, TILE_SIZE*camera_scale/2, TILE_SIZE*camera_scale/2); //Rotates angle radians around center and need to unrotate
                    at.scale(-camera_scale, camera_scale); //Scales image
                    at.translate(-TILE_SIZE, 0);
                    g2d.drawImage(i, at, io);
                }
                curx += TILE_SIZE;
            }
            curx = 0;
            cury += TILE_SIZE;
        }
    }
}
