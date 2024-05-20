package distributed.cm.server.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.common.message.DefaultMessage;
import distributed.cm.common.message.DrawListMessage;
import distributed.cm.common.message.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ClientRequestParser {

    private final ObjectMapper objectMapper;
    private final LineMessageParser lineMessageParser;
    private final CircleMessageParser circleMessageParser;
    private final SquareMassageParser squareMassageParser;
    private final TextBoxMessageParser textBoxMessageParser;

    private final Map<Integer, DrawParser> drawParserMap = new HashMap<>();

    public ClientRequestParser(ObjectMapper objectMapper, LineMessageParser lineMessageParser, CircleMessageParser circleMessageParser, SquareMassageParser squareMassageParser, TextBoxMessageParser textBoxMessageParser) {
        this.objectMapper = objectMapper;
        this.lineMessageParser = lineMessageParser;
        this.circleMessageParser = circleMessageParser;
        this.squareMassageParser = squareMassageParser;
        this.textBoxMessageParser = textBoxMessageParser;

        initialMap();
    }

    private void initialMap(){
        drawParserMap.put(1, lineMessageParser);
        drawParserMap.put(2, circleMessageParser);
        drawParserMap.put(3, circleMessageParser);
        drawParserMap.put(4, squareMassageParser);
        drawParserMap.put(5, squareMassageParser);
        drawParserMap.put(6, textBoxMessageParser);
        drawParserMap.put(7, circleMessageParser);
        drawParserMap.put(8, squareMassageParser);
    }

    public Message parse(String payload) throws JsonProcessingException {
        DefaultMessage defaultMessage = objectMapper.readValue(payload, DefaultMessage.class);

        if(defaultMessage.getMessageType() == 0) {
            return defaultMessage;
        } else if(defaultMessage.getMessageType() == 2){
            return objectMapper.readValue(payload, DrawListMessage.class);
        }

        DrawParser drawParser = drawParserMap.get(defaultMessage.getDrawType());
        return drawParser.parse(payload);
    }
}
