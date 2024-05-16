package distributed.cm.common.domain;

import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Point {
    private int x, y;

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
