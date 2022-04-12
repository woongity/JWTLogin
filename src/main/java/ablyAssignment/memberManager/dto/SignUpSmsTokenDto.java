package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpSmsTokenDto {
    private String phoneNo;
    private String token;
}
