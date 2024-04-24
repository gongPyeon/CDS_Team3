package distributed.cm.server.board;

import distributed.cm.common.domain.Draw;
import distributed.cm.server.domain.*;
import distributed.cm.server.repository.*;
import lombok.extern.slf4j.Slf4j;
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

    private final Map<String, DrawRepository> drawRepositoryMap = new HashMap<>();

    public BoardManager(LineRepository lineRepository, CircleRepository circleRepository, SquareRepository squareRepository, TextBoxRepository textBoxRepository) {
        this.lineRepository = lineRepository;
        this.circleRepository = circleRepository;
        this.squareRepository = squareRepository;
        this.textBoxRepository = textBoxRepository;

        initialDrawRepositoryMap();
    }

    private void initialDrawRepositoryMap(){
        drawRepositoryMap.put("Line", lineRepository);
        drawRepositoryMap.put("Circle", circleRepository);
        drawRepositoryMap.put("Square", squareRepository);
        drawRepositoryMap.put("TextBox", textBoxRepository);
    }

    public Map<String, Object> loadBoard(){
        Map<String, Object> draws = new HashMap<>();
        for (String key: drawRepositoryMap.keySet()) {
            DrawRepository drawRepository = drawRepositoryMap.get(key);
            draws.put(key, drawRepository.findAll());
        }
        return draws;
    }

    public void saveDraw(Draw draw){
        DrawRepository drawRepository = drawRepositoryMap.get(draw.getClass().getSimpleName());
        drawRepository.saveDraw(draw);
    }

    public void editDraw(Draw draw){
        DrawRepository drawRepository = drawRepositoryMap.get(draw.getClass().getSimpleName());
        drawRepository.updateDraw(draw);
    }
}
