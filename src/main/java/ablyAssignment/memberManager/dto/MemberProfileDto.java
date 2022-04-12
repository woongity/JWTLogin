package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberProfileDto {
    private String nickname;
    private String email;
    private String phoneNo;
    private String name;
}
