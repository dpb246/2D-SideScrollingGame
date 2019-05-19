package rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.concurrent.CopyOnWriteArrayList;

public class RenderEngine extends JPanel {
    private static RenderEngine instance = null;
    private final static  int Wanted_WIDTH = 1024;
    private final static  int Wanted_HEIGHT = 576;
    private static CopyOnWriteArrayList<Renderable> objs;
    private static Camera currentcam = null;

    private RenderEngine() {
        objs = new CopyOnWriteArrayList<>();
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        super.setDoubleBuffered(true);
        setLayout(null);
        setPreferredSize(new Dimension(Wanted_WIDTH, Wanted_HEIGHT));
    }
    public static RenderEngine getInstance() {
        if (instance == null) instance = new RenderEngine();
        return instance;
    }
    public RenderEngine setCamera(Camera newcam) {
        currentcam = newcam;
        return getInstance();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll(g, this);
        Toolkit.getDefaultToolkit().sync();
    }
    public Renderable add(Renderable obj) {
        objs.add(obj);
        return obj;
    }
    /**
     * Draws all objects in GameWorld
     * @param g Graphics to draw objects onto
     * @param io ImageObserver to pass to draw call
     */
    public void drawAll(Graphics g, ImageObserver io) {
        if (currentcam == null) throw new RuntimeException("Did not set current camera!");
        Graphics2D g2d = (Graphics2D) g;
        for (Renderable o : objs) {
            o.draw(g2d, io, currentcam.getX(), currentcam.getY(), currentcam.getZoom());
        }
    }
}
