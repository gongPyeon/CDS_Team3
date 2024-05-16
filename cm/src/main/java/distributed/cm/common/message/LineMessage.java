package distributed.cm.common.message;

import distributed.cm.common.domain.Line;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private Line draw;
}
