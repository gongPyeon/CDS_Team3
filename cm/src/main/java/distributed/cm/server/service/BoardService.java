package distributed.cm.server.service;

import distributed.cm.common.domain.Draw;
import distributed.cm.server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final DrawRepository drawRepository;

    public List<Draw> loadBoard(){
        return drawRepository.findAll();
    }

    public void saveBoard(){
        drawRepository.saveAll();
    }

    public void clearBoard() {
        drawRepository.clearAll();
    }

    public void saveDraw(Draw draw){
        drawRepository.saveDraw(draw);
    }

    public boolean editDraw(Draw draw, String sessionId){
        return drawRepository.updateDraw(draw, sessionId);
    }

    public boolean selectDraw(Draw draw, String sessionId) {
        return drawRepository.selectDraw(draw, sessionId);
    }
}
