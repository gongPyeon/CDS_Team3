package distributed.cm.server.repository;

import distributed.cm.server.domain.Shape;
import org.springframework.stereotype.Repository;

@Repository
public class LineRepository implements ShapeRepository{
    @Override
    public void save(Shape shape) {

    }
}
