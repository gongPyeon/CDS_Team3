package distributed.cm.server.repository;

import distributed.cm.common.domain.Draw;
import distributed.cm.common.domain.Line;
import distributed.cm.common.domain.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
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
        Line editLine = (Line) draw;
        Line line = (Line) lineStore.get(new Point(editLine.getX1(), editLine.getY1()));
        if (line == null) {
            log.info("No such circle=(x={},y={})", editLine.getX1(), editLine.getY1());
            return;
        }

        line.updateDraw(editLine);
    }

    @Override
    public List<Draw> findAll() {
        return lineStore.values().stream().toList();
    }
}
