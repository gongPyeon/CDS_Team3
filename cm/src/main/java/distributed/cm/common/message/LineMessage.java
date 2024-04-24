package distributed.cm.common.message;

import distributed.cm.common.domain.Line;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Line draw;

    public LineMessage() {
    }

    public LineMessage(int messageType, int drawType, Line draw) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.draw = draw;
    }
}
