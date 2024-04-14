package distributed.cm.server.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketHandler implements WebSocketHandler {

    private final EntrySocketHandler entrySocketHandler;
    private final RecieveMessageHandler recieveMessageHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        log.info("Socket connection success! (sessionId = {})", session.getId());
        try {
            entrySocketHandler.openSocketHandle(session.getId(), session);
        } catch (IOException e) {
            log.error("<{}> {}", session.getId(), e);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message){
        try {
            recieveMessageHandler.handle(session.getId(), message.getPayload().toString());
            log.info("{}", message.getPayload());
        } catch (IOException e) {
            log.error("<{}> {}", session.getId(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Socket connection closed! (sessionId = {})", session.getId());
        try{
            entrySocketHandler.closeSocketHandle(session.getId(), session);
        } catch (IOException e) {
            log.error("<{}> {}", session.getId(), e);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
