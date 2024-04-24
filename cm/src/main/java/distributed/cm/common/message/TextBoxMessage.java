package distributed.cm.common.message;

import distributed.cm.common.domain.TextBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextBoxMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private TextBox draw;

    public TextBoxMessage() {
    }

    public TextBoxMessage(int messageType, int drawType, TextBox draw) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.draw = draw;
    }
}
