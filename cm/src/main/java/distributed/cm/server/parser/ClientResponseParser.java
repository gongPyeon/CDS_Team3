package distributed.cm.server.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.common.domain.Draw;
import distributed.cm.common.message.DrawListMessage;
import distributed.cm.common.message.UserEntryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientResponseParser {

    private final ObjectMapper objectMapper;

    public String createCloseSocketMessage(String userId) {
        try{
            UserEntryMessage userEntryMessage = new UserEntryMessage(0, 1, userId);
            return objectMapper.writeValueAsString(userEntryMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String createLoadDrawMessage(List<Draw> draws) {
        try{
            DrawListMessage message = new DrawListMessage(2, draws);
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String createEditErrorMessage(){
        try {
            return objectMapper.writeValueAsString(Map.of("messageType", 3));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
