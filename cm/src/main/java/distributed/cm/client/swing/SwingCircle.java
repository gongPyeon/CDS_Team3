package distributed.cm.client.swing;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

import static java.awt.Color.*;

public class SwingCircle extends SwingShape{
    @Getter
    private int startX, startY, endX, endY;
    @Getter
    private int width,height;
    @Setter
    private Color lineColor; // 선의 색상
    // 선의 굵기 설정
    @Setter
    private int lineWidth; // 선의 굵기
    @Setter
    private Color fillColor; // 내부 색상

    public SwingCircle(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.width = Math.abs(endX - startX);
        this.height = Math.abs(endY - startY);
        this.lineColor = BLACK;
        this.lineWidth = 1;
        this.fillColor = null;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        if(fillColor != null){
            g.setColor(fillColor);
            g2d.fillOval(startX, startY, width, height);
        }

        // 테두리 그리기
        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(lineWidth));
        g2d.drawOval(startX, startY, width, height);
    }

    @Override
    public boolean contains(int mx, int my) {
        return mx >= startX && mx <= startX + width && my >= startY && my <= startY + height;
    }

    public void drawingResize(){
        int temp;
        if(startX > endX){
            temp = startX;
            startX = endX;
            endX = temp;

        }
        if(startY > endY){
            temp = startY;
            startY = endY;
            endY = temp;
        }
    }

}
