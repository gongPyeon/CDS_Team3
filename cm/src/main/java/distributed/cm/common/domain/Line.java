package distributed.cm.common.domain;


import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Line implements Draw{

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int bold;
    private String boldColor;

    public void updateDraw(Line draw){
        this.x1 = draw.getX1();
        this.x2 = draw.getX2();
        this.y1 = draw.getY1();
        this.y2 = draw.getY2();
        this.bold = draw.getBold();
        this.boldColor = draw.getBoldColor();
    }
}
