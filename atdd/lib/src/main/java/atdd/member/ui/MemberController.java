package atdd.member.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import atdd.member.application.MemberService;
import atdd.member.domain.Member;
import atdd.member.dto.AdminMemberRequest;
import atdd.member.dto.AdminMemberResponse;
import atdd.member.dto.MemberRequest;
import atdd.member.dto.MemberResponse;

@RestController
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody MemberRequest request) {
        MemberResponse member = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
    }

    @PostMapping("/members/admin")
    public ResponseEntity createAdminMember(@RequestBody MemberRequest request) {
        MemberResponse member = memberService.createAdminMember(request);
        return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
    }

    @GetMapping("/members")
    public ResponseEntity<List<AdminMemberResponse>> getMembers() {
        return ResponseEntity.ok().body(memberService.findAll());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<AdminMemberResponse> findMember(@PathVariable Long id) {
        AdminMemberResponse member = memberService.findMember(id);
        return ResponseEntity.ok().body(member);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<AdminMemberResponse> updateMember(@PathVariable Long id, @RequestBody AdminMemberRequest param) {
        memberService.updateMember(id, param);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> findMemberOfMine(MemberRequest memberRequest) {
        //MemberResponse member = memberService.findMember(memberRequest.getEmail());
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/members/me")
    public ResponseEntity<MemberResponse> updateMemberOfMine(Member loginMember, @RequestBody MemberRequest param) {
        memberService.updateMember(loginMember.getId(), param);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<MemberResponse> deleteMemberOfMine(Member loginMember) {
        memberService.deleteMember(loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}