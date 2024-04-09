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
}
