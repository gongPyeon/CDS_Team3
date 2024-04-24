package distributed.cm.server.repository;

import distributed.cm.common.domain.Draw;

import java.util.List;

public interface DrawRepository {
    void saveDraw(Draw draw);
    void updateDraw(Draw draw);
    List<Draw> findAll();
}
