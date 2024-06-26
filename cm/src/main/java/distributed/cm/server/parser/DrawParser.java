package distributed.cm.server.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import distributed.cm.common.message.Message;

public interface DrawParser {
    Message parse(String payload) throws JsonProcessingException;
}
