package shop.com.shareChat.dto.member;

import lombok.*;
import shop.com.shareChat.domain.member.Member;
import shop.com.shareChat.domain.member.Role;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto {

    private String username;
    private String password;

    public Member toEntity(BCryptPasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(this.username)
                .password(passwordEncoder.encode(this.password))
                .role(Role.USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.username, this.password);
    }
}