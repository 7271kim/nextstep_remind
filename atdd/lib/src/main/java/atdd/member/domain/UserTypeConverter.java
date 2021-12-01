package atdd.member.domain;

import javax.persistence.AttributeConverter;

import atdd.member.constant.UserType;

public class UserTypeConverter implements AttributeConverter<UserType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserType attribute) {
        return attribute.getCode();
    }

    @Override
    public UserType convertToEntityAttribute(Integer dbData) {
        return UserType.of(dbData);
    }

}
