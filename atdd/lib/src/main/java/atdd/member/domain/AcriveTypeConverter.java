package atdd.member.domain;

import javax.persistence.AttributeConverter;

import atdd.member.constant.ActiveType;

public class AcriveTypeConverter implements AttributeConverter<ActiveType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ActiveType attribute) {
        return attribute.getCode();
    }

    @Override
    public ActiveType convertToEntityAttribute(Integer dbData) {
        return ActiveType.of(dbData);
    }

}
