package ablyAssignment.memberManager.controller;

import ablyAssignment.memberManager.dto.*;
import ablyAssignment.memberManager.service.JWTService;
import ablyAssignment.memberManager.service.MemberService;
import ablyAssignment.memberManager.validator.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    private final JWTService jwtService;
    private final SignUpDtoValidator signUpDtoValidator;
    private final SignUpTokenValidator signUpTokenValidator;
    private final RefreshTokenValidator refreshTokenValidator;
    /*
        내 프로필 보기
     */
    @GetMapping("/profile/my")
    public ResponseEntity profile(HttpServletRequest request){
        MemberProfileDto memberProfileDto = memberService.getMemberProfile(request);
        return new ResponseEntity(memberProfileDto, HttpStatus.OK);
    }

    /*
        회원 가입
    */
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpDto signUpDto, Errors errors){
        Map result = new HashMap<String,String>();
        if(errors.hasErrors()){
            result.put("result","failed");
            return new ResponseEntity(result,HttpStatus.UNAUTHORIZED);
        }else {
            memberService.processNewMember(signUpDto);
            result.put("result", "success");
            return new ResponseEntity(result, HttpStatus.OK);
        }
    }

    /*
        가입시 문자 인증
     */
    @GetMapping("/sign-up/token")
    public ResponseEntity checkSignUpSmsToken(@RequestBody SignUpSmsTokenDto signUpSmsTokenDto, Errors errors){
        Map<String,String> result = new HashMap<>();

        memberService.completeSignUp(signUpSmsTokenDto);
        result.put("result","success");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /*
        비밀번호 변경
     */
    @PutMapping("/password")
    public ResponseEntity resetPassword(@RequestBody ChangePwDto changePwDto){

        Map result = new HashMap<String,String>();
        memberService.setNewPassword(changePwDto);
        result.put("result","success");
        return new ResponseEntity(result,HttpStatus.OK);
    }

    /*
        비밀번호 변경 토큰 요청
     */
    @GetMapping("/password/token")
    public ResponseEntity RequestTokenForNewPW(@RequestBody Map<String,String> map){
        String phoneNo = map.get("phoneNo");
        Map result = new HashMap<String,String>();
        memberService.processNewPassword(phoneNo);
        result.put("result","success");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /*
        이메일 로그인
     */
    @PostMapping("/login/email")
    public ResponseEntity emailLogin(@RequestBody @Valid LoginByEmailDto loginByEmailDto, Errors errors){
        Map result = new HashMap<String,String>();
        if(errors.hasErrors()){
            result.put("result","fail");
            return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
        }
        EmailLogInResponseDto response = memberService.loginByEmail(loginByEmailDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*
        전화번호 로그인
     */
    @PostMapping("login/phone-no")
    public ResponseEntity phoneNoLogin(@RequestBody @Valid LoginByPhoneNoDto loginByPhoneNoDto, Errors errors){
        Map result = new HashMap<String,String>();

        if(errors.hasErrors()){
            result.put("result","fail");
            return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
        }
        PhoneNoLoginResponseDto response = memberService.loginByPhoneNo(loginByPhoneNoDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /*
       닉네임 로그인
    */
    @PostMapping("/login/nickname")
    public ResponseEntity nicknameLogin(@RequestBody @Valid LoginByNicknameDto loginByNicknameDto, Errors errors){
        Map result = new HashMap<String,String>();
        if(errors.hasErrors()){
            result.put("result","fail");
            return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
        }
        NicknameLogInResponseDto response = memberService.loginByNickname(loginByNicknameDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/token/reissued")
    public ResponseEntity reIssueToken(@RequestBody TokenDto tokenDto,Errors errors){
        refreshTokenValidator.validate(tokenDto,errors);
        Map result = new HashMap<String,String>();
        if(errors.hasErrors()){
            result.put("result","fail");
            return new ResponseEntity(result, HttpStatus.UNAUTHORIZED);
        }
        TokenDto reissuedTokenDto = jwtService.reIssueToken(tokenDto);
        return new ResponseEntity(reissuedTokenDto,HttpStatus.OK);
    }
}
