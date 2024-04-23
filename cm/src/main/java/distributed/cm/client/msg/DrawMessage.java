package distributed.cm.client.msg;

import distributed.cm.server.domain.Draw;

public interface DrawMessage extends Message{
    int getDrawType();
    Draw getDraw();
    Draw getEditDraw();
}
