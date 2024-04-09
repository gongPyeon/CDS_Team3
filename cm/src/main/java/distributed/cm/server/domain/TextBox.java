package distributed.cm.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextBox implements Draw{
    private int x1, y1;
    private String text;
    private String fontColor;
    private int bold;
}
