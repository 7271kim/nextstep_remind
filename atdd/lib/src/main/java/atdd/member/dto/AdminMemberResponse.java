package atdd.member.dto;

import atdd.member.domain.Member;

public class AdminMemberResponse extends MemberResponse {
    private Integer userType;
    private Integer activeType;

    public AdminMemberResponse() {}

    public AdminMemberResponse(Long id, String email, Integer age, Integer userType, Integer activeType) {
        super(id, email, age);
        this.userType = userType;
        this.activeType = activeType;
    }

    public Integer getUserType() {
        return userType;
    }

    public Integer getActiveType() {
        return activeType;
    }

    public static AdminMemberResponse of(Member member) {
        return new AdminMemberResponse(member.getId(), member.getEmail(), member.getAge(), member.getUserType().getCode(), member.getActiveType().getCode());
    }
}
