package distributed.cm.server.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Circle implements Draw{

    private int x1, x2, y1, y2;

    private int bold;
    private String boldColor;

    private int isPaint;
    private String paintColor;
}
