package distributed.cm.server.domain;

import lombok.Getter;

@Getter
public class Circle extends Shape{

    public Circle(int x1, int y1, int x2, int y2, int bold, boolean isPaint, String color) {
        super(x1, y1, x2, y2, bold, isPaint, color);
    }

}
