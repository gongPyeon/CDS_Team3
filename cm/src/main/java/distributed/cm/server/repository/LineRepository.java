package distributed.cm.server.repository;

import distributed.cm.server.domain.Circle;
import distributed.cm.server.domain.Draw;
import distributed.cm.server.domain.Line;
import distributed.cm.server.domain.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LineRepository implements DrawRepository {

    private static final ConcurrentHashMap<Point, Draw> lineStore = new ConcurrentHashMap<>();

    @Override
    public void saveDraw(Draw draw) {
        Line line = (Line) draw;
        Point point = new Point(line.getX1(), line.getY1());
        lineStore.put(point, line);
    }

    @Override
    public void updateDraw(Draw draw) {
    }

    @Override
    public List<Draw> findAll() {
        return lineStore.values().stream().toList();
    }
}
