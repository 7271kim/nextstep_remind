package atdd.member.dto;

import atdd.member.domain.Member;

public class MemberRequest {
    private String email;
    private String password;
    private Integer age;

    public MemberRequest() {}

    public MemberRequest(String email, String password, Integer age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member toMember() {
        return new Member(email, password, age);
    }
}
