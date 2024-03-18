package distributed.cm.server.parser;

import org.springframework.stereotype.Component;

@Component
public class ClientResponseParser {

    public String createOpenSocketMessage(String sessionId){
        return "Success Connection!";
    }

    public String createCloseSocketMessage(String sessionId){
        return "";
    }
}
