package rendering;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Level_Tile {
    private CopyOnWriteArrayList<Renderable> objs; //ya ya its slow
    private double x, y;
    public final static int TILE_SIZE = 80;
    public Level_Tile(double x, double y) {
        objs = new CopyOnWriteArrayList<>();
        this.x = x;
        this.y = y;
    }
    public Level_Tile load_from_file(String file_path) {
        Image spike = (new ImageIcon("resources/Pirate Adventure Textures/Other Sprites/spikes.png")).getImage();
        Image floor = (new ImageIcon("resources/Pirate Adventure Textures/wood_floor_large.png")).getImage();
        try {
            ArrayList<String> stream = new ArrayList<>(Files.lines(Paths.get(file_path)).collect(Collectors.toList()));
            Collections.reverse(stream);
            double curx = 0;
            double cury = 0;
            for (String line : stream) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '^': //spike resources/Pirate Adventure Textures/Other Sprites/spikes.png
                            add(new Renderable(x + curx + TILE_SIZE/2, y + cury + TILE_SIZE/2, TILE_SIZE, TILE_SIZE, spike));
                            break;
                        case '=': //ground resources/Pirate Adventure Textures/wood_floor_large.png
                            add(new Renderable(x + curx + TILE_SIZE/2, y + cury + TILE_SIZE/2, TILE_SIZE, TILE_SIZE, floor));
                            break;
                        case '*': //air
                            //do NOTHING LETS GO PARTY TIME
                            break;
                        case 'G': //goal
                            // TODO: I mean did you really want to be able to end the level?
                            break;
                        case 'T': //tree resources/Pirate Adventure Textures/Other Sprites/palm_tree_flipped.png
                            add(new Renderable(x + curx + 320/2, y + cury + 360/2, 320, 360, "resources/Pirate Adventure Textures/Other Sprites/palm_tree.png"));
                            curx += 360 - TILE_SIZE;
                            break;
                    }
                    curx += TILE_SIZE;
                }
                curx = 0;
                cury += TILE_SIZE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    public Renderable add(Renderable obj) {
        objs.add(obj);
        return obj;  //Return obj passed to make nicer usage
    }
    public CopyOnWriteArrayList getAll() {
        return objs;
    }

    public void draw(Graphics2D g2d, ImageObserver io, Camera currentCam) {
        for (Renderable o : objs) {
            if(o.shouldDelete()) {
                objs.remove(o);
                continue;
            }
            o.draw(g2d, io, currentCam.getPos(), currentCam.getZoom());
        }
    }
}
