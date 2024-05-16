package distributed.cm.server.repository;

import distributed.cm.common.domain.Draw;
import distributed.cm.common.domain.Point;
import distributed.cm.common.domain.TextBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class TextBoxRepository implements DrawRepository{

    private static final ConcurrentHashMap<Point, Draw> textBoxStore = new ConcurrentHashMap<>();

    @Override
    public void saveDraw(Draw draw) {
        TextBox textBox = (TextBox) draw;
        Point point = new Point(textBox.getX1(), textBox.getY1());
        textBoxStore.put(point, draw);
    }

    @Override
    public void updateDraw(Draw draw) {
        TextBox editTextBox = (TextBox) draw;

        TextBox textBox = (TextBox) textBoxStore.get(new Point(editTextBox.getX1(), editTextBox.getY1()));
        if (textBox == null) {
            log.info("No such textBox=(x={},y={})", editTextBox.getX1(), editTextBox.getY1());
            return;
        }

        textBox.updateDraw(editTextBox);
    }

    @Override
    public List<Draw> findAll() {
        return textBoxStore.values().stream().toList();
    }
}
