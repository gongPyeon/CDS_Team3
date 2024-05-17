package distributed.cm.server.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketHandler implements WebSocketHandler {

    private final EntrySocketHandler entrySocketHandler;
    private final ReceiveMessageHandler receiveMessageHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        log.info("Socket connection success! (sessionId = {})", session.getId());
        entrySocketHandler.openSocketHandle(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message){
        log.info("message{}", message.getPayload());
        receiveMessageHandler.handle(session.getId(), message.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Socket connection closed! (sessionId = {})", session.getId());
        entrySocketHandler.closeSocketHandle(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
