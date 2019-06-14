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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Level_Tile {
    private ArrayList<ArrayList<Image>> images; //ya ya its slow
    private double x, y;
    public final static int TILE_SIZE = 80;
    public Level_Tile(double x, double y) {
        images = new ArrayList<>();
        this.x = x;
        this.y = y;
    }
    public Level_Tile load_from_file(String file_path) {
        Image spike = (new ImageIcon("resources/Pirate Adventure Textures/Other Sprites/spikes.png")).getImage();
        Image floor = (new ImageIcon("resources/Pirate Adventure Textures/wood_floor_large.png")).getImage();
        try {
            ArrayList<String> stream = new ArrayList<>(Files.lines(Paths.get(file_path)).collect(Collectors.toList()));
            Collections.reverse(stream);
            int cury = 0;
            PhysicsWorld world = PhysicsWorld.getInstance();
            for (String line : stream) {
                int curx = 0;
                for (char c : line.toCharArray()) {
                    images.add(new ArrayList<>());
                    switch (c) {
                        case '^': //spike resources/Pirate Adventure Textures/Other Sprites/spikes.png
                            images.get(cury).add(spike);
                            world.add(new AABB(new Vector2D(curx*TILE_SIZE + TILE_SIZE/2, cury*TILE_SIZE + (TILE_SIZE-10)/2), TILE_SIZE, TILE_SIZE-10, 0)).bitmask = 1;
                            break;
                        case '=': //ground resources/Pirate Adventure Textures/wood_floor_large.png
                            images.get(cury).add(floor);
                            world.add(new AABB(new Vector2D(curx*TILE_SIZE + TILE_SIZE/2, cury*TILE_SIZE + TILE_SIZE/2), TILE_SIZE, TILE_SIZE, 0)).bitmask = 1;
                            break;
                        case '*': //air
                            //do NOTHING LETS GO PARTY TIME
                            images.get(cury).add(null);
                            break;
                        case 'G': //goal
                            // TODO: I mean did you really want to be able to end the level?
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

    public void draw(Graphics2D g2d, ImageObserver io, Vector2D camera_shift, double camera_scale) {
        double curx = 0;
        double cury = 0;
        for (int j = 0; j < images.size(); j++) {
            ArrayList<Image> row = images.get(j);
            int k = (int) Math.max(0, Math.floor((camera_shift.getX()-x)/(TILE_SIZE)));
            for (k = 0; k < row.size() && (x+curx-camera_shift.getX())*camera_scale < RenderEngine.Wanted_WIDTH+TILE_SIZE*2; k++) {
                Image i = row.get(k);
                if (i != null) {
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
