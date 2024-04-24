package distributed.cm.client.swing;

import lombok.Getter;

import java.awt.*;

public class SwingPencil extends SwingShape{
    @Getter
    private int startX, startY, endX, endY;

    public SwingPencil(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        // 선의 굵기를 3픽셀로 설정
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(startX, startY, endX, endY);
    }

    @Override
    public boolean contains(int mx, int my) {
        return mx >= startX && mx <= startX && my >= startY && my <= startY;
    }
}
