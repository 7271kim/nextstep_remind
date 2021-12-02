package atdd.member.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import atdd.member.constant.UserType;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndUserType(String email, UserType admin);
}
