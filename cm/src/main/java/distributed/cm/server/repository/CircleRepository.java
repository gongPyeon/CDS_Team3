package distributed.cm.server.repository;

import distributed.cm.common.domain.Circle;
import distributed.cm.common.domain.Draw;
import distributed.cm.common.domain.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
        Circle editCircle = (Circle) draw;

        Circle circle = (Circle) circleStore.get(new Point(editCircle.getX1(), editCircle.getY1()));
        if (circle == null) {
            log.info("No such circle=(x={},y={})", editCircle.getX1(), editCircle.getY1());
            return;
        }

        circle.updateDraw(editCircle);
    }

    @Override
    public List<Draw> findAll() {
        return circleStore.values().stream().toList();
    }
}
