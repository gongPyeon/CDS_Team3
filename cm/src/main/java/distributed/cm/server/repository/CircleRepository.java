package distributed.cm.server.repository;

import distributed.cm.server.domain.Circle;
import distributed.cm.server.domain.Draw;
import distributed.cm.server.domain.Point;
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

        circle.setX2(editCircle.getX2());
        circle.setY2(editCircle.getY2());
        circle.setBold(editCircle.getBold());
        circle.setBoldColor(editCircle.getBoldColor());
        circle.setIsPaint(editCircle.getIsPaint());
        circle.setPaintColor(editCircle.getPaintColor());
    }

    @Override
    public List<Draw> findAll() {
        return circleStore.values().stream().toList();
    }
}
