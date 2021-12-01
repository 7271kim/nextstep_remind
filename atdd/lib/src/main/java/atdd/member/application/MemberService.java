package atdd.member.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.member.domain.Member;
import atdd.member.domain.MemberRepository;
import atdd.member.dto.MemberRequest;
import atdd.member.dto.MemberResponse;

@Service
@Transactional
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        return MemberResponse.of(member);
    }

    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        member.update(param.toMember());
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(InputException::new);
        memberRepository.delete(member);
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(MemberResponse::of).collect(Collectors.toList());
    }
}
