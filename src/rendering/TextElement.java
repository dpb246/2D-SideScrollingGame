package rendering;

import java.awt.*;

public class TextElement {
    public String message;
    public int x, y;
    private Font f;
    private Color c;
    public TextElement(String mes, int x, int y, Font f, Color c) {
        this.message = mes;
        this.x = x;
        this.y = y;
        this.f = f;
        this.c = c;
    }
    public TextElement(String mes, int x, int y) {
        this(mes, x, y, new Font("Arial", Font.PLAIN, 40), Color.YELLOW);
    }
    public void draw(Graphics2D g) {
        g.setColor(c);
        g.setFont(f);
        g.drawString(message, x, y);
    }

}
