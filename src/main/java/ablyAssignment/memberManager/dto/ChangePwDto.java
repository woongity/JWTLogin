package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePwDto {
    private String phoneNo;
    private String token;
    private String newPassword;
}
