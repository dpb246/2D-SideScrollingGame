package rendering;

import javax.xml.soap.Text;
import java.awt.*;

public class TextElement {
    private String message;
    private int x, y;
    private Font f;
    private Color c;
    private boolean visible;
    public TextElement(String mes, int x, int y, Color c, Font f) {
        this.message = mes;
        this.x = x;
        this.y = y;
        this.f = f;
        this.c = c;
        this.visible = true;
    }
    public TextElement(String mes, int x, int y, int font_size) {
        this(mes, x, y, Color.YELLOW, new Font("Arial", Font.PLAIN, font_size));
    }
    public TextElement(String mes, int x, int y) {
        this(mes, x, y, Color.YELLOW, new Font("Arial", Font.PLAIN, 40));
    }
    public TextElement(String mes, int x, int y, Color c) {
        this(mes, x, y, c, new Font("Arial", Font.PLAIN, 40));
    }
    public void draw(Graphics2D g) {
        Color previousColor = g.getColor();
        Font previousFont = g.getFont();
        g.setColor(c);
        g.setFont(f);
        g.drawString(message, x, y);
        g.setColor(previousColor);
        g.setFont(previousFont);
    }
    public TextElement setVisiblity(boolean value) {
        this.visible = value;
        return this;
    }
    public TextElement setMessage(String mes) {
        this.message = mes;
        return this;
    }
    public TextElement setX(int x) {
        this.x = x;
        return this;
    }
    public TextElement setY(int y) {
        this.y = y;
        return this;
    }
    public TextElement setColor(Color c) {
        this.c = c;
        return this;
    }
}
