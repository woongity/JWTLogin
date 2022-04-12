package ablyAssignment.memberManager.jwt;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByNickname(username);
        CustomUserDetails userDetails = new CustomUserDetails(
                member.getId(),member.getNickname(),member.getPassword(),member.getAuthority());
        return userDetails;
    }
}
