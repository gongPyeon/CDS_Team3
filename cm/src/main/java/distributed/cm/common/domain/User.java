package distributed.cm.common.domain;

import lombok.Getter;

@Getter
public class User {
    private final String sessionId;
    private String userId;

    public User(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }
}
