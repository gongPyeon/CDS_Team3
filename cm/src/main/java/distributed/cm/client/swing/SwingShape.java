package distributed.cm.client.swing;

import java.awt.*;

public abstract class SwingShape {
    public abstract boolean contains(int mx, int my);
    protected Color lineColor; // 선의 색상

    public SwingShape() {
        this.lineColor = Color.BLACK; // 기본 선의 색상은 검정색
    }

    // 선의 색상 설정
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    // 선의 색상 반환
    public Color getLineColor() {
        return lineColor;
    }

    // 그리기 메서드
    public abstract void draw(Graphics g);
}
