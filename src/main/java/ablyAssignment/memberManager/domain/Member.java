package ablyAssignment.memberManager.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter @Setter @EqualsAndHashCode(of="id")
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNo;

    private String name;

    private String authority = "ROLE_USER";

    private LocalDateTime joinedDateTime;
    private boolean smsVerified;
    private String smsCheckToken;

    private String refreshToken;

    public Member(String nickname, String password, String phoneNo, String name,String email) {
        this.nickname = nickname;
        this.password = password;
        this.phoneNo = phoneNo;
        this.name = name;
        this.email = email;
    }

    public void generateSMSCheckToken(){
        this.smsCheckToken = UUID.randomUUID().toString();
    }

    public void completeSignUp() {
        this.setSmsVerified(true);
        this.setJoinedDateTime(LocalDateTime.now());
    }
    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
