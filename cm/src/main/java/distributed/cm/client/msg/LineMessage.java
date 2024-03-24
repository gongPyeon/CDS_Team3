package distributed.cm.client.msg;

import distributed.cm.server.domain.Line;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineMessage {
    private int messageType;
    private int drawType;
    private Line line;

    public LineMessage(int messageType, int drawType, Line line) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.line = line;
    }
}
