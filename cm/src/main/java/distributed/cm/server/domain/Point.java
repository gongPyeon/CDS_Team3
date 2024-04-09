package distributed.cm.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {
    private int x, y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("equals호출");
        Point point = (Point) obj;
        if (x == point.getX() && y == point.getY()) return true;
        else return false;
    }
}
