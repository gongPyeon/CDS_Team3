package distributed.cm.client.msg;

import distributed.cm.server.domain.Circle;
import distributed.cm.server.domain.Draw;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CircleMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Circle draw;
    private Circle editDraw;

    public CircleMessage() {
    }

    public CircleMessage(int messageType, int drawType, Circle draw) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.draw = draw;
    }
}
