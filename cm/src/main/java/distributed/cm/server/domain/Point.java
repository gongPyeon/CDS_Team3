package distributed.cm.server.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
        Point point = (Point) obj;
        if (x == point.getX() && y == point.getY()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
