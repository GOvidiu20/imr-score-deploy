package breakout.vr.BreakoutVR.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestPlayerScore {
    private String username;
    private Long score;

}
