package distributed.cm.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TextBox implements Draw{

    @JsonIgnore
    private ReentrantLock updateLock = new ReentrantLock();

    DrawType type = DrawType.TEXTBOX;

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

    public TextBox(TextBox textBox){
        this.x1 = textBox.getX1();
        this.y1 = textBox.getY1();
        this.text = textBox.getText();
        this.fontColor = textBox.getFontColor();
        this.bold = textBox.getBold();
    }

    @Override
    public boolean updateDraw(Draw editDraw, String sessionId){
        TextBox textBox = (TextBox) editDraw;
        if (updateLock.tryLock()) {
            try {
                this.x1 = textBox.getX1();
                this.y1 = textBox.getY1();
                this.text = textBox.getText();
                this.fontColor = textBox.getFontColor();
                this.bold = textBox.getBold();
                return true;
            } finally {
                updateLock.unlock();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean selectDraw(String sessionId) {
        return false;
    }
}
