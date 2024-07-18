package shop.com.shareChat.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.com.shareChat.domain.member.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResDto {
    private String email;

    public static MemberResDto of(Member member) {
        return new MemberResDto(member.getUsername());
    }
}