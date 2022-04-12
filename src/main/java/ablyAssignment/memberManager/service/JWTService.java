package ablyAssignment.memberManager.service;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.TokenDto;
import ablyAssignment.memberManager.exception.ErrorCode;
import ablyAssignment.memberManager.exception.NotFoundMemberException;
import ablyAssignment.memberManager.jwt.JwtProvider;
import ablyAssignment.memberManager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class JWTService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    public String getTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader("Bearer");
        return token;
    }

    @Transactional
    public TokenDto reIssueToken(TokenDto tokenDto) {

        Member member = memberRepository.findMemberByRefreshToken(tokenDto.getJwtRefreshToken());
        if(member==null){
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        }
        String accessToken = jwtProvider.generateToken(member.getNickname());
        String refreshToken = jwtProvider.generateRefreshToken();
        member.updateRefreshToken(refreshToken);
        return new TokenDto(accessToken, refreshToken);
    }
}
