package distributed.cm.server;

import distributed.cm.server.domain.Shape;
import distributed.cm.server.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BoardManager {

    private final LineRepository lineRepository;
    private final CircleRepository circleRepository;
    private final SquareRepository squareRepository;
    private final TextBoxRepository textBoxRepository;

    public BoardManager(LineRepository lineRepository, CircleRepository circleRepository, SquareRepository squareRepository, TextBoxRepository textBoxRepository) {
        this.lineRepository = lineRepository;
        this.circleRepository = circleRepository;
        this.squareRepository = squareRepository;
        this.textBoxRepository = textBoxRepository;
    }

    //TODO
    public void loadBoard(){

    }

    //TODO
    public void saveCircle(Shape shape) {
    }

}
