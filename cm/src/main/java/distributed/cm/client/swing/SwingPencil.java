package distributed.cm.client.swing;

import lombok.Getter;

import java.awt.*;

public class SwingPencil extends SwingShape{
    @Getter
    private int startX, startY, currX, currY;

    public SwingPencil(int startX, int startY, int currX, int currY) {
        this.startX = startX;
        this.startY = startY;
        this.currX = currX;
        this.currY = currY;
    }

    public void draw(Graphics g){
        g.drawLine(startX, startY, currX, currY);
        //g.dispose();
        startX = currX;
        startY = currY;
    }

    @Override
    public boolean contains(int mx, int my) {
        return mx >= startX && mx <= startX && my >= startY && my <= startY;
    }
}
