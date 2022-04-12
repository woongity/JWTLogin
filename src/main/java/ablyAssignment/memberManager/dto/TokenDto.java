package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
