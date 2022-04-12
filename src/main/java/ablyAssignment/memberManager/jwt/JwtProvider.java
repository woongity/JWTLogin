package ablyAssignment.memberManager.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final CustomUserDetailsService memberDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-valid-time}")
    private Long tokenValidTime;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.refresh-token-valid-time}")
    private Long refreshTokenValidTime;

    public Authentication getAuthentication(String token){
        UserDetails userDetails =memberDetailsService.loadUserByUsername(getNickname(token));

        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }


    public String getNickname(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public boolean validateTokenExpiration(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            log.info("만료된 토큰입니다");
            return false;
        }
    }

    // 토큰 얻어오기
    public String getTokenFromHeader(HttpServletRequest request){
        return request.getHeader(tokenHeader);
    }

    // 리프레쉬 토큰 생성
    public String generateRefreshToken(){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    public String generateToken(String nickname){
        Claims claims = Jwts.claims().setSubject(nickname);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }
}
