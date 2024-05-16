package distributed.cm.common.message;

import distributed.cm.common.domain.Circle;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CircleMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Circle draw;
}
