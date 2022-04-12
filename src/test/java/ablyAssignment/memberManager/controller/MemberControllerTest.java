package ablyAssignment.memberManager.controller;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.*;
import ablyAssignment.memberManager.repository.MemberRepository;
import ablyAssignment.memberManager.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:/insertMember.sql")
public class MemberControllerTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void 이메일_로그인_테스트() throws Exception {

        LoginByEmailDto loginByEmailDto = new LoginByEmailDto("ytw5702@naver.com", "ghatyvld2@");
        String content = objectMapper.writeValueAsString(loginByEmailDto);

        mockMvc.perform(post("/api/login/email")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Transactional
    @Test
    public void 전화번호_로그인_테스트() throws Exception {
        LoginByPhoneNoDto loginByPhoneNoDto = new LoginByPhoneNoDto("010-5754-3873", "ghatyvld2@");
        String content = objectMapper.writeValueAsString(loginByPhoneNoDto);

        mockMvc.perform(post("/api/login/phone-no")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 닉네임_로그인_테스트() throws Exception {
        LoginByNicknameDto loginByNicknameDto = new LoginByNicknameDto("andy", "ghatyvld2@");
        String content = objectMapper.writeValueAsString(loginByNicknameDto);

        mockMvc.perform(post("/api/login/nickname")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 비밀번호_변경_테스트() throws Exception {
        String token = memberRepository.findMemberByPhoneNo("010-5754-3873").getSmsCheckToken();
        ChangePwDto changePwDto = new ChangePwDto("010-5754-3873", token, "dowanttogoably");
        String content = objectMapper.writeValueAsString(changePwDto);
        mockMvc.perform(put("/api/password")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 회원_가입_테스트() throws Exception {
        SignUpDto signUpDto = new SignUpDto("name", "nickname", "ghatyvld2@", "010-3333-3333", "ytw@naver.com");
        String content = objectMapper.writeValueAsString(signUpDto);
        mockMvc.perform(post("/api/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 회원_가입_이메일_인증테스트() throws Exception {
        SignUpDto signUpDto = new SignUpDto("name","nickname","password","010-5754-3333","ytw57@naver.com");
        memberService.processNewMember(signUpDto);
        Member member = memberRepository.findMemberByNickname("nickname");
        SignUpSmsTokenDto signUpSmsTokenDto = new SignUpSmsTokenDto("010-5754-3333",member.getSmsCheckToken());
        String content = objectMapper.writeValueAsString(signUpSmsTokenDto);
        mockMvc.perform(get("/api/sign-up/token")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void 사용자_프로필보기_테스트() throws Exception{
        LoginByEmailDto loginByEmailDto = new LoginByEmailDto("ytw5702@naver.com","ghatyvld2@");
        EmailLogInResponseDto response = memberService.loginByEmail(loginByEmailDto);
        String token = response.getJwtAccessToken();
        mockMvc.perform(get("/api/profile/my")
                .header("Bearer",token)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
