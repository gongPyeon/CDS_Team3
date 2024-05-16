package distributed.cm.common.message;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntryMessage implements Message{
    private int messageType;
    private int entry;
    private String userId;
}
