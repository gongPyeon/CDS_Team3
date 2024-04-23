package distributed.cm.client.msg;

import distributed.cm.server.domain.TextBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextBoxMessage implements DrawMessage{
    private int messageType;
    private int drawType;
    private TextBox draw;
    private TextBox editDraw;

    public TextBoxMessage() {
    }

    public TextBoxMessage(int messageType, int drawType, TextBox draw) {
        this.messageType = messageType;
        this.drawType = drawType;
        this.draw = draw;
    }
}
