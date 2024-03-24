package distributed.cm.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TextBox { //값이 수정될 수도 있어서 final 처리 x

    private int x1;
    private int y1;
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
}
