package distributed.cm.server.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.client.msg.DefaultMessage;
import distributed.cm.client.msg.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClientRequestParser {

    private final ObjectMapper objectMapper;
    private final LineMessageParser lineMessageParser;
    private final CircleMessageParser circleMessageParser;
    private final RectangleMassageParser rectangleMassageParser;
    private final TextBoxMessageParser textBoxMessageParser;

    private final Map<Integer, DrawParser> drawParserMap = new HashMap<>();

    public ClientRequestParser(ObjectMapper objectMapper, LineMessageParser lineMessageParser, CircleMessageParser circleMessageParser, RectangleMassageParser rectangleMassageParser, TextBoxMessageParser textBoxMessageParser) {
        this.objectMapper = objectMapper;
        this.lineMessageParser = lineMessageParser;
        this.circleMessageParser = circleMessageParser;
        this.rectangleMassageParser = rectangleMassageParser;
        this.textBoxMessageParser = textBoxMessageParser;

        initialMap();
    }

    private void initialMap(){
        drawParserMap.put(1, lineMessageParser);
        drawParserMap.put(2, circleMessageParser);
        drawParserMap.put(3, circleMessageParser);
        drawParserMap.put(4, rectangleMassageParser);
        drawParserMap.put(5, rectangleMassageParser);
        drawParserMap.put(6, textBoxMessageParser);
    }

    public Message parse(String payload) throws JsonProcessingException {
        DefaultMessage defaultMessage = objectMapper.readValue(payload, DefaultMessage.class);

        if(defaultMessage.getMessageType() == 0) return defaultMessage;

        DrawParser drawParser = drawParserMap.get(defaultMessage.getDrawType());
        return drawParser.parse(payload);
    }
}
