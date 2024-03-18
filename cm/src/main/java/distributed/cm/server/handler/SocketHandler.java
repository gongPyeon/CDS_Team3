package distributed.cm.server.handler;

import distributed.cm.server.BoardManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RequiredArgsConstructor
public class SocketHandler implements WebSocketHandler {

    private final EntrySocketHandler entrySocketHandler;
    private final RecieveMessageHandler recieveMessageHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Socket connection success! (sessionId = {})", session.getId());
        entrySocketHandler.openSocket(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("<{}> {}", session.getId(), message.getPayload().toString());
        recieveMessageHandler.recieveMessage(session.getId(), message.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("SocketHandler.handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket connection closed! (sessionId = {})", session.getId());
        entrySocketHandler.closeSocket(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        log.info("SocketHandler.supportsPartialMessages");
        return false;
    }
}
