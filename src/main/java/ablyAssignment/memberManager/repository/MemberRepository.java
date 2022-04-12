package ablyAssignment.memberManager.repository;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.TokenDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {


    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNo(String phoneNo);
    Member findMemberByEmail(String email);
    Member findMemberByNickname(String nickname);
    Member findMemberByPhoneNo(String phoneNo);
    Member findMemberByRefreshToken(String refreshToken);
}
