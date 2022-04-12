package ablyAssignment.memberManager.service;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.*;
import ablyAssignment.memberManager.exception.*;
import ablyAssignment.memberManager.jwt.JwtProvider;
import ablyAssignment.memberManager.repository.MemberRepository;

import ablyAssignment.memberManager.sms.SMS;
import ablyAssignment.memberManager.sms.SMSSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final JWTService jwtService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final SMSSender smsSender;

    @Transactional
    public void setNewPassword(ChangePwDto changePwDto) {
        Member member = memberRepository.findMemberByPhoneNo(changePwDto.getPhoneNo());
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        } else if (!member.getSmsCheckToken().equals(changePwDto.getToken())) {
            throw new TokenNotFoundException("유효하지 않은 토큰입니다", ErrorCode.INVALID_TOKEN);
        } else {
            String newEncodedPassword = passwordEncoder.encode(changePwDto.getNewPassword());
            member.setPassword(newEncodedPassword);
        }
    }

    @Transactional
    public void processNewMember(SignUpDto signUpDto) {
        if (memberRepository.existsByEmail(signUpDto.getEmail())) {
            throw new DuplicatedMemberException("중복된 이메일 입니다", ErrorCode.DUPLICATED_MEMBER);
        } else if (memberRepository.existsByNickname(signUpDto.getNickname())) {
            throw new DuplicatedMemberException("중복된 닉네임 입니다", ErrorCode.DUPLICATED_MEMBER);
        } else if (memberRepository.existsByPhoneNo(signUpDto.getPhoneNo())) {
            throw new DuplicatedMemberException("중복된 전화번호 입니다", ErrorCode.DUPLICATED_MEMBER);
        } else {
            Member member = saveMember(signUpDto);
            member.generateSMSCheckToken();
            sendVerifySms(member);
        }
    }

    @Transactional
    public void processNewPassword(String phoneNo) {
        log.info(phoneNo);
        Member member = memberRepository.findMemberByPhoneNo(phoneNo);
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        }
        member.generateSMSCheckToken();
        sendVerifySms(member);
    }

    public void sendVerifySms(Member member) {
        SMS sms = new SMS(member.getPhoneNo(), member.getSmsCheckToken());
        smsSender.sendMessage(sms);
    }

    public Member saveMember(SignUpDto signUpDto) {
        String password = passwordEncoder.encode(signUpDto.getPassword());
        Member member = new Member(signUpDto.getNickname(), password, signUpDto.getPhoneNo(), signUpDto.getName(), signUpDto.getEmail());
        return memberRepository.save(member);
    }

    @Transactional
    public EmailLogInResponseDto loginByEmail(LoginByEmailDto loginByEmailDto) {
        Member member = memberRepository.findMemberByEmail(loginByEmailDto.getEmail());
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        } else if (!member.isSmsVerified()) {
            throw new NotSmsVerifiedException("sms 토큰이 인증되지 않은 사용자입니다", ErrorCode.NOT_SMS_VERIFIED_USER);
        } else if (!passwordEncoder.matches(loginByEmailDto.getPassword(), member.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호 입니다", ErrorCode.ERROR_PASSWORD);
        } else {
            String refreshToken = jwtProvider.generateRefreshToken();
            member.updateRefreshToken(refreshToken);
            return new EmailLogInResponseDto(member.getEmail(), jwtProvider.generateToken(member.getNickname()), member.getRefreshToken());
        }
    }

    @Transactional
    public PhoneNoLoginResponseDto loginByPhoneNo(LoginByPhoneNoDto loginByPhoneNoDto) {
        Member member = memberRepository.findMemberByPhoneNo(loginByPhoneNoDto.getPhoneNo());
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        } else if (!member.isSmsVerified()) {
            throw new NotSmsVerifiedException("sms 토큰이 인증되지 않은 사용자입니다", ErrorCode.NOT_SMS_VERIFIED_USER);
        } else if (!passwordEncoder.matches(loginByPhoneNoDto.getPassword(), member.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호 입니다", ErrorCode.ERROR_PASSWORD);
        } else {
            String refreshToken = jwtProvider.generateRefreshToken();
            member.updateRefreshToken(refreshToken);
            return new PhoneNoLoginResponseDto(member.getPhoneNo(), jwtProvider.generateToken(member.getNickname()), member.getRefreshToken());
        }
    }

    @Transactional
    public NicknameLogInResponseDto loginByNickname(LoginByNicknameDto loginByNicknameDto) {
        Member member = memberRepository.findMemberByNickname(loginByNicknameDto.getNickname());
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        } else if (!member.isSmsVerified()) {
            throw new NotSmsVerifiedException("sms 토큰이 인증되지 않은 사용자입니다", ErrorCode.NOT_SMS_VERIFIED_USER);
        } else if (!passwordEncoder.matches(loginByNicknameDto.getPassword(), member.getPassword())) {
            throw new WrongPasswordException("잘못된 비밀번호 입니다", ErrorCode.ERROR_PASSWORD);
        } else {
            String refreshToken = jwtProvider.generateRefreshToken();
            member.updateRefreshToken(refreshToken);
            return new NicknameLogInResponseDto(member.getNickname(), jwtProvider.generateToken(member.getNickname()), member.getRefreshToken());
        }
    }

    public MemberProfileDto getMemberProfile(HttpServletRequest request) {
        String nickName = jwtProvider.getNickname(jwtService.getTokenFromHeader(request));
        Member member = memberRepository.findMemberByNickname(nickName);
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        }
        MemberProfileDto memberProfileDto = new MemberProfileDto(member.getNickname(), member.getEmail(), member.getPhoneNo(), member.getName());
        return memberProfileDto;
    }


    @Transactional
    public void completeSignUp(SignUpSmsTokenDto signUpSmsTokenDto) {
        Member member = memberRepository.findMemberByPhoneNo(signUpSmsTokenDto.getPhoneNo());
        if (member == null) {
            throw new NotFoundMemberException("존재하지 않는 유저입니다", ErrorCode.MEMBER_NOT_FOUND);
        }
        else if(!member.getSmsCheckToken().equals(signUpSmsTokenDto.getToken())){
            throw new TokenNotFoundException("유효하지 않은 토큰입니다",ErrorCode.INVALID_TOKEN);
        }
        else{
            member.completeSignUp();
        }
    }
}
