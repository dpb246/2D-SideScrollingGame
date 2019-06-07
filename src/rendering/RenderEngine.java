package rendering;

import main.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.concurrent.CopyOnWriteArrayList;

public class RenderEngine extends JPanel {
    private static RenderEngine instance = null;
    public final static  int Wanted_WIDTH = 1366;
    public final static  int Wanted_HEIGHT = 768;
    private static CopyOnWriteArrayList<Renderable> objs; //ya ya its slow
    private static CopyOnWriteArrayList<TextElement> uiText; //ya ya its slow
    private static Level_Tile currentLevel = null;
    private static Camera currentcam = null;

    private RenderEngine() {
        objs = new CopyOnWriteArrayList<>();
        uiText = new CopyOnWriteArrayList<>();
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        super.setDoubleBuffered(true);
        setLayout(null);
        setPreferredSize(new Dimension(Wanted_WIDTH, Wanted_HEIGHT));
        setMaximumSize(new Dimension(Wanted_WIDTH, Wanted_HEIGHT));
        setMinimumSize(new Dimension(Wanted_WIDTH, Wanted_HEIGHT));

        repaint();
    }
    public static RenderEngine getInstance() {
        if (instance == null) instance = new RenderEngine();
        return instance;
    }
    public RenderEngine setBackgroundColor(Color c) {
        this.setBackground(c);
        return getInstance();
    }
    public RenderEngine setCamera(Camera newcam) {
        currentcam = newcam;
        return getInstance(); //cause why not
    }
    public RenderEngine setLevel(Level_Tile newLevel) {
        currentLevel = newLevel;
        return getInstance(); //cause why not
    }
    public Camera getCurrentcam() {
        return currentcam;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll(g, this);
        Toolkit.getDefaultToolkit().sync();
    }
    public Renderable add(Renderable obj) {
        objs.add(obj);
        return obj;  //Return obj passed to make nicer usage
    }
    public TextElement addText(String mesg, int x, int y) {
        TextElement t = new TextElement(mesg, x, y);
        uiText.add(t);
        return t;
    }
    /**
     * Draws all objects in GameWorld
     * @param g Graphics to draw objects onto
     * @param io ImageObserver to pass to draw call
     */
    public void drawAll(Graphics g, ImageObserver io) {
        if (currentcam == null) throw new RuntimeException("Did not set current camera!");
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0.0, Wanted_HEIGHT);
        g2d.scale(1.0, -1.0);

        if (currentLevel != null) {
            currentLevel.draw(g2d, io, currentcam.getPos(), currentcam.getZoom());
        }

        for (Renderable o : objs) {
            if(o.shouldDelete()) {
                objs.remove(o);
                continue;
            }
            o.draw(g2d, io, currentcam.getPos(), currentcam.getZoom());
        }
        g2d.scale(1.0, -1.0);
        g2d.translate(0.0, -Wanted_HEIGHT);
        for (TextElement t : uiText) {
            t.draw(g2d);
        }
    }
}
