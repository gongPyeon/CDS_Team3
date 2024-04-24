package distributed.cm.common.message;

import distributed.cm.common.domain.Draw;

public interface DrawMessage extends Message{
    int getDrawType();
    Draw getDraw();
}
