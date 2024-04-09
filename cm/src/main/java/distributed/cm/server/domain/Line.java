package distributed.cm.server.domain;


import lombok.Getter;

@Getter
public class Line implements Draw{

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int bold;
    private String color;

    public Line(int x1, int y1, int x2, int y2, int bold, String color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.bold = bold;
        this.color = color;
    }


}
