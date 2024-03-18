package distributed.cm.server;

import distributed.cm.server.domain.Circle;
import distributed.cm.server.domain.Line;
import distributed.cm.server.domain.Shape;
import distributed.cm.server.domain.Square;
import distributed.cm.server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class BoardManager {

    private final LineRepository lineRepository;
    private final CircleRepository circleRepository;
    private final SquareRepository squareRepository;
    private final TextBoxRepository textBoxRepository;

    private final Map<String, ShapeRepository> repositoryMap = new HashMap<>();

    public BoardManager(LineRepository lineRepository, CircleRepository circleRepository, SquareRepository squareRepository, TextBoxRepository textBoxRepository) {
        this.lineRepository = lineRepository;
        this.circleRepository = circleRepository;
        this.squareRepository = squareRepository;
        this.textBoxRepository = textBoxRepository;
    }

    {
        setRepositoryMap();
    }

    //TODO
    private void setRepositoryMap(){
        repositoryMap.put("Line", lineRepository);
        repositoryMap.put("Circle", circleRepository);
        repositoryMap.put("Square", squareRepository);
        repositoryMap.put("TextBox", textBoxRepository);
    }

    //TODO
    public void loadBoard(){

    }

    //TODO
    public void saveBoard(Shape shape) {
/*        String classTypeName = shape.getClass().getSimpleName();

        ShapeRepository shapeRepository = repositoryMap.get(classTypeName);
        shapeRepository.save(shape);*/
    }

}
