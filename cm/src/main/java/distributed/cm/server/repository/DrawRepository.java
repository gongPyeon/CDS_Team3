package distributed.cm.server.repository;

import distributed.cm.common.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class DrawRepository {

    private Map<Point, Draw> drawStores = Collections.synchronizedMap(new LinkedHashMap<>());
    private Map<Point, Draw> formerStores  = Collections.synchronizedMap(new LinkedHashMap<>());

    public void saveDraw(Draw draw) {
        Point point = new Point(draw.getX1(), draw.getY1());
        drawStores.put(point, draw);
    }

    public boolean updateDraw(Draw editDraw, String sessionId) {
        Draw draw = drawStores.get(new Point(editDraw.getX1(), editDraw.getY1()));
        if (draw == null) {
            log.info("No such circle=(x={},y={})", editDraw.getX1(), editDraw.getY1());
            throw new NoSuchElementException("Can't not update editDraw:No such editDraw.");
        }

        return draw.updateDraw(editDraw, sessionId);
    }

    public List<Draw> findAll() {
        return drawStores.values().stream().toList();
    }

    public boolean selectDraw(Draw selectDraw, String sessionId) {
        Draw draw = drawStores.get(new Point(selectDraw.getX1(), selectDraw.getY1()));
        if (draw == null) {
            log.info("No such circle=(x={},y={})", selectDraw.getX1(), selectDraw.getY1());
            throw new NoSuchElementException("Can't not update editDraw:No such editDraw.");
        }

        return draw.updateDraw(selectDraw, sessionId);
    }

    public void saveAll(){
        formerStores.clear();
        synchronized (drawStores){
            for (Draw draw : drawStores.values()) {
                if(draw instanceof Line){
                    Line line = (Line) draw;
                    Line copyLine = new Line(line);
                    formerStores.put(new Point(line.getX1(), line.getY1()), copyLine);
                } else if (draw instanceof Circle) {
                    Circle circle = (Circle) draw;
                    Circle copyCircle = new Circle(circle);
                    formerStores.put(new Point(copyCircle.getX1(), copyCircle.getY1()), copyCircle);
                } else if (draw instanceof Square) {
                    Square square = (Square) draw;
                    Square copySquare = new Square(square);
                    formerStores.put(new Point(copySquare.getX1(), copySquare.getY1()), copySquare);
                } else if (draw instanceof TextBox) {
                    TextBox textBox = (TextBox) draw;
                    TextBox copyTextBox = new TextBox(textBox);
                    formerStores.put(new Point(copyTextBox.getX1(), copyTextBox.getY1()), copyTextBox);
                }
            }
        }
    }

    public List<Draw> findFormalAll() {
        drawStores.clear();
        drawStores = formerStores;
        formerStores = Collections.synchronizedMap(new LinkedHashMap<>());
        return drawStores.values().stream().toList();
    }

    public void clearAll(){
        drawStores.clear();
    }
}
