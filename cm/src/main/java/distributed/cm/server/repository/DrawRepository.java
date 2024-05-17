package distributed.cm.server.repository;

import distributed.cm.common.domain.Circle;
import distributed.cm.common.domain.Draw;
import distributed.cm.common.domain.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DrawRepository {

    private final Map<Point, Draw> drawStores = new ConcurrentHashMap<>();

    public void saveDraw(Draw draw) {
        Point point = new Point(draw.getX1(), draw.getY1());
        drawStores.put(point, draw);
    }

    public void updateDraw(Draw draw) {
        Circle editCircle = (Circle) draw;

        Circle circle = (Circle) drawStores.get(new Point(editCircle.getX1(), editCircle.getY1()));
        if (circle == null) {
            log.info("No such circle=(x={},y={})", editCircle.getX1(), editCircle.getY1());
            return;
        }

        circle.updateDraw(editCircle);
    }

    public List<Draw> findAll() {
        return drawStores.values().stream().toList();
    }
}
