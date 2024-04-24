package distributed.cm.client.message;

import distributed.cm.server.domain.Square;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquareMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Square draw;

    public SquareMessage() {
    }

    public SquareMessage(int messageType, int drawType, Square draw) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.draw = draw;
    }
}
