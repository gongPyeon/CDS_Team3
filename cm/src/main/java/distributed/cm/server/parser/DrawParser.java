package distributed.cm.server.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import distributed.cm.client.msg.Message;

public interface DrawParser {
    Message parse(String payload) throws JsonProcessingException;
}
