package distributed.cm.common.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import distributed.cm.common.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultMessage implements Message{
    private int messageType;
    private int entry;
    private int drawType;
    private String userId;
    private User usr;
}
