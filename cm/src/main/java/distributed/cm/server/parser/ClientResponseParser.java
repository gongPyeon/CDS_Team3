package distributed.cm.server.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import distributed.cm.client.msg.UserEntryMessage;
import distributed.cm.server.domain.Draw;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientResponseParser {

    private final ObjectMapper objectMapper;

    public String createOpenSocketMessage(String sessionId) throws JsonProcessingException {
        Map<String, String> message = Map.of("Message", "Success connection!", "sessionId", sessionId);
        return objectMapper.writeValueAsString(message);
    }

    public String createCloseSocketMessage(String userId) throws JsonProcessingException {
        UserEntryMessage userEntryMessage = new UserEntryMessage(0, 1, userId);
        return objectMapper.writeValueAsString(userEntryMessage);
    }

    public String createAllDrawsMessage(Map<String, Object> draws) throws JsonProcessingException {
        draws.put("messageType", 1);
        draws.put("drawType", 0);
        return objectMapper.writeValueAsString(draws);
    }
}
