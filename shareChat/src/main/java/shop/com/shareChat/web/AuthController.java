package shop.com.shareChat.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.com.shareChat.auth.jwt.dto.TokenDto;
import shop.com.shareChat.auth.jwt.dto.TokenReqDto;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.member.MemberReqDto;
import shop.com.shareChat.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     *  JWT 로그인 API
     * @param memberRequestDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberReqDto memberRequestDto) {
        TokenDto tokenDto = authService.login(memberRequestDto);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "회원가입 성공", tokenDto), HttpStatus.CREATED);
    }

    /**
     * JWT 토큰 재 발급 
     * @param tokenReqDto
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenReqDto tokenReqDto) {
        return ResponseEntity.ok(authService.refreshToken(tokenReqDto));
    }
}