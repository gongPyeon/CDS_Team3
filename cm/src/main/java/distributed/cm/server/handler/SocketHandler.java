package distributed.cm.server.handler;

import distributed.cm.server.response.ClientMessageResponser;
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
    private final ReceiveMessageHandler receiveMessageHandler;
    private final ClientMessageResponser clientMessageResponser;

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
            String returnMessage = receiveMessageHandler.handle(session.getId(), message.getPayload().toString());
            clientMessageResponser.sendMessageAllSocket(returnMessage, session.getId());
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
            String returnMessage = entrySocketHandler.closeSocketHandle(session.getId());
            clientMessageResponser.sendMessageAllSocket(returnMessage, session.getId());
        } catch (IOException e) {
            log.error("<{}> {}", session.getId(), e);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
