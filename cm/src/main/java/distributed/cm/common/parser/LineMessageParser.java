package distributed.cm.common.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.common.message.LineMessage;
import distributed.cm.common.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineMessageParser implements DrawParser{

    private final ObjectMapper objectMapper;

    @Override
    public Message parse(String payload) throws JsonProcessingException {
        return objectMapper.readValue(payload, LineMessage.class);
    }
}
