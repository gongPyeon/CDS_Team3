package distributed.cm.server.repository;

import distributed.cm.common.domain.Draw;
import distributed.cm.common.domain.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class DrawRepository {

    private final Map<Point, Draw> drawStores = new LinkedHashMap<>();

    public void saveDraw(Draw draw) {
        Point point = new Point(draw.getX1(), draw.getY1());
        drawStores.put(point, draw);
    }

    public boolean updateDraw(Draw editDraw) {
        Draw draw = drawStores.get(new Point(editDraw.getX1(), editDraw.getY1()));
        if (draw == null) {
            log.info("No such circle=(x={},y={})", editDraw.getX1(), editDraw.getY1());
            throw new NoSuchElementException("Can't not update editDraw:No such editDraw.");
        }

        return draw.updateDraw(editDraw);
    }

    public List<Draw> findAll() {
        return drawStores.values().stream().toList();
    }
}
