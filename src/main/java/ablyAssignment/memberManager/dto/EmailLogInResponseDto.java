package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailLogInResponseDto {
    String email;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
