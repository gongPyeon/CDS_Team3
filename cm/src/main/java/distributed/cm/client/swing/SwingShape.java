package distributed.cm.client.swing;

import java.awt.*;

public abstract class SwingShape {
    public abstract boolean contains(int mx, int my);

    public void drawingResize(){ };

    // 그리기 메서드
    public abstract void draw(Graphics g);
}
