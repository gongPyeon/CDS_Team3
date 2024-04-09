package distributed.cm.server.repository;

import distributed.cm.server.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SquareRepository implements DrawRepository{

    private static final ConcurrentHashMap<Point, Draw> squareStore = new ConcurrentHashMap<>();

    @Override
    public void saveDraw(Draw draw) {
        Square square = (Square) draw;
        Point point = new Point(square.getX1(), square.getY1());
        squareStore.put(point, draw);
    }

    @Override
    public void updateDraw(Draw draw) {

    }

    @Override
    public List<Draw> findAll() {
        return squareStore.values().stream().toList();
    }
}
