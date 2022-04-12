package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NicknameLogInResponseDto {
    private String nickname;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
