package distributed.cm.client.msg;

import distributed.cm.server.domain.Shape;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RectangleMessage {
    private int messageType;
    private int drawType;
    private Shape shape;

    public RectangleMessage(int messageType, int drawType, Shape shape) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.shape = shape;
    }
}
