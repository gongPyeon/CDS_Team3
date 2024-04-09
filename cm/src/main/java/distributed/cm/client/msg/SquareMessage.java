package distributed.cm.client.msg;

import distributed.cm.server.domain.Square;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquareMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Square draw;
}
