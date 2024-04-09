package distributed.cm.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextBox implements Draw{
    private int x1, y1;
    private String text;
    private String fontColor;
    private int bold;

    public TextBox() {
    }

    public TextBox(int x1, int y1, String text, String fontColor, int bold) {
        this.x1 = x1;
        this.y1 = y1;
        this.text = text;
        this.fontColor = fontColor;
        this.bold = bold;
    }
}
