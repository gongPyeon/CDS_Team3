package distributed.cm.common.domain;

import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TextBox implements Draw{

    private ReentrantLock lock = new ReentrantLock();

    private int x1, y1;
    private String text;
    private String fontColor;
    private int bold;

    public void updateDraw(TextBox draw){
        lock.lock();
        try {
            this.x1 = draw.getX1();
            this.y1 = draw.getY1();
            this.text = draw.getText();
            this.fontColor = draw.getFontColor();
            this.bold = draw.getBold();
        } finally{
            lock.unlock();
        }
    }
}
