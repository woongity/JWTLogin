package ablyAssignment.memberManager.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class LoginByNicknameDto {
    @NotBlank
    String nickname;
    @NotBlank
    String password;
}
