package rendering;

import main.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * Object that extends JPanel to provide helper functions for drawing Renderables to the screen
 */
public class RenderEngine extends JPanel {
    private static RenderEngine instance = null;
    public final static  int Wanted_WIDTH = 1366;
    public final static  int Wanted_HEIGHT = 768;
    private static CopyOnWriteArrayList<Renderable> objs; //ya ya its slow
    private static CopyOnWriteArrayList<TextElement> uiText; //ya ya its slow
    private static Level_Tile currentLevel = null;
    private static Camera currentcam = null;
    /**
     * its a constructor idk what you expect
     */
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

    /**
     * Get singleton of the class, never want more than one for a game
     * @return
     */
    public static RenderEngine getInstance() {
        if (instance == null) instance = new RenderEngine();
        return instance;
    }

    /**
     * I mean you can read the method name i hope
     * @param c
     * @return
     */
    public RenderEngine setBackgroundColor(Color c) {
        this.setBackground(c);
        return getInstance();
    }

    /**
     * Sets the current camera
     * @param newcam
     * @return
     */
    public RenderEngine setCamera(Camera newcam) {
        currentcam = newcam;
        return getInstance(); //cause why not
    }

    /**
     * Sets the current level tile, i.e. background to draw
     * @param newLevel
     * @return
     */
    public RenderEngine setLevel(Level_Tile newLevel) {
        currentLevel = newLevel;
        return getInstance(); //cause why not
    }

    /**
     * Clears all objects from the objs array
     */
    public void removeAllRenderables() {
        objs.clear();
    }
    /**
     * Clears all objects from the uiText array
     */
    public void removeAllText() {
        uiText.clear();
    }

    /**
     *
     * @return the current camera
     */
    public Camera getCurrentcam() {
        return currentcam;
    }

    /**
     * Gets called on OS repaints, seperate thread, can be requested using repaint() DO NOT DIRECT CALL
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll(g, this);
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Add Renderable to be drawn every frame
     * @param obj
     * @return
     */
    public Renderable add(Renderable obj) {
        objs.add(obj);
        return obj;  //Return obj passed to make nicer usage
    }

    /**
     * Creates and returns a text element
     * @param mesg
     * @param x
     * @param y
     * @return
     */
    public TextElement addText(String mesg, int x, int y) {
        TextElement t = new TextElement(mesg, x, y);
        uiText.add(t);
        return t;
    }
    /**
     * Creates and returns a text element with a different font size WWWOOOWWW
     * @param mesg
     * @param x
     * @param y
     * @return
     */
    public TextElement addText(String mesg, int x, int y, int fontsize) {
        TextElement t = new TextElement(mesg, x, y, fontsize);
        uiText.add(t);
        return t;
    }
    /**
     * Draws all objects in GameWorld
     * @param g Graphics to draw objects onto
     * @param io ImageObserver to pass to draw call
     */
    public void drawAll(Graphics g, ImageObserver io) {
        currentcam.update();
        
        if (currentcam == null) throw new RuntimeException("Did not set current camera!");
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0.0, Wanted_HEIGHT); //Flip y to be 0 at bottom
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

        g2d.scale(1.0, -1.0); //Unflip before drawing text otherwise text is drawn mirrored
        g2d.translate(0.0, -Wanted_HEIGHT);
        for (TextElement t : uiText) {
            t.draw(g2d);
        }
    }
}
