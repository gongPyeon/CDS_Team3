package distributed.cm.server.repository;


import distributed.cm.common.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class UserRepository {
    private static final ConcurrentHashMap<String, User> userStore = new ConcurrentHashMap();

    public void saveUser(String sessionId, User user) {
        userStore.put(sessionId, user);
        log.info("Save user : sessionId={}, userId={}", sessionId, user.getUserId());
    }

    public String removeUser(String sessionId){
        User user = userStore.get(sessionId);
        if (user == null) {
            throw new NoSuchElementException("No such user.");
        }

        userStore.remove(sessionId);
        return user.getUserId();
    }

    public User findUserBySessionId(String sessionId){
        try {
            return userStore.get(sessionId);
        }catch (NullPointerException e){
            return null;
        }
    }
}
