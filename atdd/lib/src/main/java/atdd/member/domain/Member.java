package atdd.member.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import atdd.common.BaseEntity;
import atdd.member.constant.ActiveType;
import atdd.member.constant.UserType;

@Entity
public class Member extends BaseEntity {
    public static final Member EMTPY = new Member();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private Integer age;

    @Convert(converter = UserTypeConverter.class)
    private UserType userType;

    @Convert(converter = AcriveTypeConverter.class)
    private ActiveType activeType;

    public Member() {}

    public Member(String email, String password, Integer age, Integer userType, Integer activeType) {
        this.email = email;
        this.password = password;
        this.age = age;
        this.userType = UserType.of(userType);
        this.activeType = ActiveType.of(activeType);
    }

    public Member(String email, String password, Integer age) {
        this(email, password, age, 0, 1);
    }

    public Long getId() {
        return id;
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

    public UserType getUserType() {
        return userType;
    }

    public ActiveType getActiveType() {
        return activeType;
    }

    public void update(Member member) {
        this.email = member.email;
        this.password = member.password;
        this.age = member.age;
        this.userType = member.userType;
        this.activeType = member.activeType;
    }

    public void update(String email, int age) {
        this.email = email;
        this.age = age;
    }
}
