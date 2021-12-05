package atdd.member.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.member.constant.ActiveType;
import atdd.member.constant.UserType;
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

    @Transactional(readOnly = true)
    @Cacheable(value = "member", key = "#id")
    public AdminMemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        return AdminMemberResponse.of(member);
    }

    @CachePut(value = "member", key = "#id")
    public void updateMember(Long id, AdminMemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        member.update(param.toMember());
    }

    @CachePut(value = "member", key = "#id")
    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        param.setPassword(passwordEncoder.encode(param.getPassword()));
        member.update(param.toMember());
    }

    @CacheEvict(value = "member", key = "#id")
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public List<AdminMemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(AdminMemberResponse::of).collect(Collectors.toList());
    }

    public MemberResponse createAdminMember(MemberRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = memberRepository
            .save(new Member("rootAdmin", request.getPassword(), request.getAge(), UserType.ADMIN.getCode(), ActiveType.ACTIVE.getCode()));
        return MemberResponse.of(member);
    }
}
