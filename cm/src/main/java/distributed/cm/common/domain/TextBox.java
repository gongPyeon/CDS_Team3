package distributed.cm.common.domain;

import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextBox implements Draw{

    private ReentrantLock lock = new ReentrantLock();

    private int x1, y1;
    private String text;
    private String fontColor;
    private int bold;

    public TextBox(int x1, int y1, String text, String fontColor, int bold) {
        this.x1 = x1;
        this.y1 = y1;
        this.text = text;
        this.fontColor = fontColor;
        this.bold = bold;
    }

    @Override
    public void updateDraw(Draw draw){
        TextBox textBox = (TextBox) draw;
        lock.lock();
        try {
            this.x1 = textBox.getX1();
            this.y1 = textBox.getY1();
            this.text = textBox.getText();
            this.fontColor = textBox.getFontColor();
            this.bold = textBox.getBold();
        } finally{
            lock.unlock();
        }
    }
}
