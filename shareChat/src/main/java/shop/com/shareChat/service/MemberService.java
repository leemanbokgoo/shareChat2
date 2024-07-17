package shop.com.shareChat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.domain.member.Member;
import shop.com.shareChat.domain.member.repository.MemberRepository;
import shop.com.shareChat.dto.member.JoinReqDto;
import shop.com.shareChat.dto.member.JoinResDto;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private  final BCryptPasswordEncoder passwordEncoder;

    /**
     *  회원가입
     * @param joinReqDto
     * @return
     */
    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto){
        // 동일 회원이 존재하는지 중복 검사
        Optional<Member> memberOP = memberRepository.findByUsername(joinReqDto.getUsername());
        if ( memberOP.isPresent()) {
            throw new CustomApiException(ErrorCode.USER_EXIST);
        }
        // 패스워드 인코딩
        Member memberPS = memberRepository.save(joinReqDto.toEntity(passwordEncoder));
        // dto 응답
        return new JoinResDto(memberPS);
    }
}
