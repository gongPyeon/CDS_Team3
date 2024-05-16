package distributed.cm.common.message;

import distributed.cm.common.domain.Line;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DrawListMessage implements Message {
    private int messageType;
    private List<Line> line;
    private List<Line> circle;
    private List<Line> textBox;
    private List<Line> square;
}
