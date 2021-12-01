package atdd.member.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.member.domain.Member;
import atdd.member.domain.MemberRepository;
import atdd.member.dto.AdminMemberRequest;
import atdd.member.dto.AdminMemberResponse;
import atdd.member.dto.MemberRequest;
import atdd.member.dto.MemberResponse;

@Service
@Transactional
public class MemberService {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse createMember(MemberRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public AdminMemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        return AdminMemberResponse.of(member);
    }

    public void updateMember(Long id, AdminMemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        member.update(param.toMember());
    }

    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        member.update(param.toMember());
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        memberRepository.delete(member);
    }

    public List<AdminMemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(AdminMemberResponse::of).collect(Collectors.toList());
    }
}
