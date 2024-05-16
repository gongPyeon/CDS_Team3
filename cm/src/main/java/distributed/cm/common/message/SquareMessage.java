package distributed.cm.common.message;

import distributed.cm.common.domain.Square;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SquareMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Square draw;
}
