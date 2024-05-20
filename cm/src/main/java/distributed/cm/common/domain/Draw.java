package distributed.cm.common.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Circle.class, name = "CIRCLE"),
        @JsonSubTypes.Type(value = Line.class, name = "LINE"),
        @JsonSubTypes.Type(value = Square.class, name = "SQUARE"),
        @JsonSubTypes.Type(value = TextBox.class, name = "TEXTBOX")
})
public interface Draw {
    boolean updateDraw(Draw editDraw, String sessionId);
    boolean selectDraw(String sessionId);
    int getX1();
    int getY1();
}
