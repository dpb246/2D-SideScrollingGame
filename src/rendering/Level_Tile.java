package rendering;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
public class Level_Tile {
    private CopyOnWriteArrayList<Renderable> objs; //ya ya its slow
    private double x, y;
    public Level_Tile(double x, double y) {
        objs = new CopyOnWriteArrayList<>();
        this.x = x;
        this.y = y;
    }
    public boolean load_from_file(String file_path) {
        try {
            Scanner sc = new Scanner(new File(file_path));
            while (sc.hasNext()) {
                int count = 0;
                double[] buffer = new double[6];
                while (sc.hasNextDouble()) {
                    buffer[count] = sc.nextDouble();
                    count++;
                }
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
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
