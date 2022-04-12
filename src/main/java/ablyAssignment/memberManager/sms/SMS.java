package ablyAssignment.memberManager.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SMS {
    private String to;
    private final String from = "010-5754-3876";
    private String text;
}
