package distributed.cm.common.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Circle implements Draw{
    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    private int isPaint;
    private String paintColor;

    public Circle() {
    }

    public Circle(int x1, int x2, int y1, int y2, int bold, String boldColor, String paintColor) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.bold = bold;
        this.boldColor = boldColor;
        this.paintColor = paintColor;
    }

    public Circle(int startX, int startY, int bold, String boldColor, String paintColor){
        this.x1 = startX;
        this.y2 = startY;
        this.bold = bold;
        this.boldColor = boldColor;
        this.paintColor = paintColor;
    }
}
