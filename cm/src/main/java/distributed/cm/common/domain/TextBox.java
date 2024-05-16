package distributed.cm.common.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TextBox implements Draw{
    private int x1, y1;
    private String text;
    private String fontColor;
    private int bold;

    public void updateDraw(TextBox draw){
        this.x1 = draw.getX1();
        this.y1 = draw.getY1();
        this.text = draw.getText();
        this.fontColor = draw.getFontColor();
        this.bold = draw.getBold();
    }
}
