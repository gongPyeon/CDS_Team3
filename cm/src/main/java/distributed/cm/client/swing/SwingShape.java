package distributed.cm.client.swing;

import java.awt.*;

public abstract class SwingShape {
    public abstract boolean contains(int mx, int my);
    protected String lineColor; // 선의 색상

    public SwingShape() {
        this.lineColor = "#000000"; // 기본 선의 색상은 검정색
    }

    // 그리기 메서드
    public abstract void draw(Graphics g);
}
