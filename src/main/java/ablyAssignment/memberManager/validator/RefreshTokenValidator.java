package ablyAssignment.memberManager.validator;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.SignUpDto;
import ablyAssignment.memberManager.dto.TokenDto;
import ablyAssignment.memberManager.jwt.JwtProvider;
import ablyAssignment.memberManager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator implements Validator {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(TokenDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TokenDto tokenDto = (TokenDto) target;
        Member member = memberRepository.findMemberByRefreshToken(tokenDto.getJwtRefreshToken());

        if (!jwtProvider.validateTokenExpiration(tokenDto.getJwtRefreshToken())) {
            errors.rejectValue("jwtRefreshToken","expired.token","만료된 리프레쉬 토큰입니다");
        }
        if (!member.getRefreshToken().equals(tokenDto.getJwtRefreshToken())) {
            errors.rejectValue("jwtRefreshToken","wrong.token","잘못된 리프레쉬 토큰입니다");
        }
    }
}
