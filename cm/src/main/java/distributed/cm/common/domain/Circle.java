package distributed.cm.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Circle implements Draw{
    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    private int isPaint;
    private String paintColor;

    public void updateDraw(Circle draw){
        this.x1 = draw.getX1();
        this.x2 = draw.getX2();
        this.y1 = draw.getY1();
        this.y2 = draw.getY2();
        this.bold = draw.getBold();
        this.isPaint = draw.getIsPaint();
        this.paintColor = draw.getPaintColor();
    }
}
