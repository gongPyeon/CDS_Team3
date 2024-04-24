package distributed.cm.client.message;

import distributed.cm.server.domain.Draw;

public interface DrawMessage extends Message{
    int getDrawType();
    Draw getDraw();
}
