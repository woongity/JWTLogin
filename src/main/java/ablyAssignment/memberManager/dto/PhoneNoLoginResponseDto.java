package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PhoneNoLoginResponseDto {
    String email;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
