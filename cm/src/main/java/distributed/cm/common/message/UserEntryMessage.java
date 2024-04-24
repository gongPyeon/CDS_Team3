package distributed.cm.common.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntryMessage implements Message{
    private int messageType;
    private int entry;
    private String userId;

    public UserEntryMessage() {
    }

    public UserEntryMessage(int messageType, int entry, String userId) {
        this.messageType = messageType;
        this.entry = entry;
        this.userId = userId;
    }
}
