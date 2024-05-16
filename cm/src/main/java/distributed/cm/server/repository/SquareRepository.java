package distributed.cm.server.repository;

import distributed.cm.common.domain.Draw;
import distributed.cm.common.domain.Point;
import distributed.cm.common.domain.Square;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
        Square editSquare = (Square) draw;

        Square square = (Square) squareStore.get(new Point(editSquare.getX1(), editSquare.getY1()));
        if (square == null) {
            log.info("No such circle=(x={},y={})", editSquare.getX1(), editSquare.getY1());
            return;
        }

        square.updateDraw(editSquare);
    }

    @Override
    public List<Draw> findAll() {
        return squareStore.values().stream().toList();
    }

}
