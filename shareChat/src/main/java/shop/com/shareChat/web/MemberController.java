package shop.com.shareChat.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.com.shareChat.dto.HttpResponseDto;
import shop.com.shareChat.dto.member.JoinReqDto;
import shop.com.shareChat.dto.member.JoinResDto;
import shop.com.shareChat.dto.member.MemberResDto;
import shop.com.shareChat.ex.CustomValidationException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.MemberService;
import shop.com.shareChat.util.SecurityUtil;

@RequestMapping("/api/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 API
     * @param joinReqDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomValidationException(ErrorCode.VALIDATION_ERROR, null);
        }
        JoinResDto joinRespDto = memberService.join(joinReqDto);
        return new ResponseEntity<>(new HttpResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }

    // test용 API
    @GetMapping("/me")
    public ResponseEntity<MemberResDto> findMemberInfoById() {
        System.out.println(SecurityUtil.getCurrentMemberId());
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }
}
