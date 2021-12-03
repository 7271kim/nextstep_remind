package atdd.path.constant;

import java.util.Arrays;

import atdd.member.domain.Member;

public enum DiscountRange {
    ADULT(19, 1, 0),
    ADOLESCENT(13, 0.8, 350),
    CHILD(6, 0.5, 350),
    INFANT(0, 0.0, 350);

    private int startRange;
    private double discountRate;
    private int statndard;

    private DiscountRange(int startRange, double discountRate, int statndard) {
        this.startRange = startRange;
        this.discountRate = discountRate;
        this.statndard = statndard;
    }

    public static DiscountRange findByMember(Member member) {
        if (member.isNull()) {
            return ADULT;
        }
        return Arrays.stream(DiscountRange.values())
            .filter(value -> member.getAge() >= value.startRange)
            .findFirst()
            .orElse(ADULT);

    }

    public double getDiscountRate() {
        return discountRate;
    }

    public int getStatndard() {
        return statndard;
    }
}
