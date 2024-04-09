package distributed.cm.client.msg;

import distributed.cm.server.domain.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CircleMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Circle draw;
}
