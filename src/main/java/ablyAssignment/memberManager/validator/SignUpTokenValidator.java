package ablyAssignment.memberManager.validator;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.ChangePwDto;
import ablyAssignment.memberManager.dto.LoginByEmailDto;
import ablyAssignment.memberManager.dto.SignUpSmsTokenDto;
import ablyAssignment.memberManager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
@RequiredArgsConstructor
public class SignUpTokenValidator implements Validator {
    private final MemberRepository memberRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpSmsTokenDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpSmsTokenDto signUpSmsTokenDto = (SignUpSmsTokenDto) target;
        if(!memberRepository.existsByPhoneNo(signUpSmsTokenDto.getPhoneNo())) {
            errors.rejectValue("phoneNo", "invalid.phoneNo", new Object[]{signUpSmsTokenDto.getPhoneNo()}, "등록되지 않은 전화번호 입니다");
        }
        Member member = memberRepository.findMemberByPhoneNo(signUpSmsTokenDto.getPhoneNo());
        if(!member.getSmsCheckToken().equals(signUpSmsTokenDto.getToken())){
            errors.rejectValue("token", "invalid.token", new Object[]{signUpSmsTokenDto.getToken()}, "잘못된 토큰 입니다");
            log.info("옳지 않은 토큰입니다");
        }
    }
}
