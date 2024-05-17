package distributed.cm.common.message;

import distributed.cm.common.domain.Draw;
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
    private List<Draw> draw;
}
