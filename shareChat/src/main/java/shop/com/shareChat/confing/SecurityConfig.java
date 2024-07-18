package shop.com.shareChat.confing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import shop.com.shareChat.auth.jwt.JwtAccessDeniedHandler;
import shop.com.shareChat.auth.jwt.JwtAuthenticationEntryPoint;
import shop.com.shareChat.auth.jwt.JwtFilter;
import shop.com.shareChat.auth.jwt.TokenProvider;
import shop.com.shareChat.domain.member.Role;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] paths = {
                "/api/member/**",
        };

        http
            // CSRF 설정 Disable
            .csrf(csrf -> csrf.disable())
            // 예외 처리 핸들러 설정
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            // 동일 출처에서만 프레임 허용
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            // 세션 관리 설정: 상태 없음
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // 로그인, 회원가입 API는 인증 없이 접근 허용
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .requestMatchers(paths).hasRole(Role.USER.name())
                .requestMatchers("/auth/**").permitAll()
                // .anyRequest().authenticated() // 나머지 요청은 인증 필요
                .anyRequest().permitAll()
            )
            // JwtSecurityConfig 적용
            .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

}