package atdd.member.dto;

import atdd.member.domain.Member;

public class AdminMemberRequest extends MemberRequest {
    private Integer userType;
    private Integer activeType;

    public AdminMemberRequest() {}

    public AdminMemberRequest(String email, String password, Integer age, Integer userType, Integer activeType) {
        super(email, password, age);
        this.userType = userType;
        this.activeType = activeType;
    }

    public Integer getUserType() {
        return userType;
    }

    public Integer getActiveType() {
        return activeType;
    }

    @Override
    public Member toMember() {
        return new Member(getEmail(), getPassword(), getAge(), userType, activeType);
    }

}
