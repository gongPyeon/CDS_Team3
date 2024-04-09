package distributed.cm.server.repository;

import distributed.cm.server.domain.Circle;
import distributed.cm.server.domain.Draw;
import distributed.cm.server.domain.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CircleRepository implements DrawRepository{

    private static final ConcurrentHashMap<Point, Draw> circleStore = new ConcurrentHashMap<>();

    @Override
    public void saveDraw(Draw draw) {
        Circle circle = (Circle) draw;
        Point point = new Point(circle.getX1(), circle.getY1());
        circleStore.put(point, draw);
    }

    @Override
    public void updateDraw(Draw draw) {

    }

    @Override
    public List<Draw> findAll() {
        return circleStore.values().stream().toList();
    }
}
