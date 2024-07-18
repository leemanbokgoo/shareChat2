package shop.com.shareChat.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.com.shareChat.domain.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
        Optional<Member> findByUsername(String username);
        boolean existsByUsername(String username);
}
