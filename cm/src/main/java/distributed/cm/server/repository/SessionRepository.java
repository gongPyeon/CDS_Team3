package distributed.cm.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class SessionRepository {

    private static final ConcurrentHashMap<String, WebSocketSession> sessionStore = new ConcurrentHashMap();

    public void saveSession(String sessionId, WebSocketSession session){
        sessionStore.put(sessionId, session);
    }

    public void removeSession(String sessionId){
        sessionStore.remove(sessionId);
    }

    public List<WebSocketSession> findAllSessions(){
        return new ArrayList<WebSocketSession>(sessionStore.values());
    }
}
