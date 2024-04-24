package distributed.cm.common.message;

import distributed.cm.common.domain.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CircleMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Circle draw;

    public CircleMessage() {
    }

    public CircleMessage(int messageType, int drawType, Circle draw) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.draw = draw;
    }
}
