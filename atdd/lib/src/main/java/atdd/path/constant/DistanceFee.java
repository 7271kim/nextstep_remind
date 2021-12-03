package atdd.path.constant;

import java.util.Arrays;

public enum DistanceFee {

    SECTION_ONE(50, 8, 100),
    SECTION_TWO(10, 5, 100),
    BASE_FEE(0, 0, 1250);

    private int startRange;
    private int divide;
    private int fee;

    private DistanceFee(int startRange, int divide, int fee) {
        this.startRange = startRange;
        this.divide = divide;
        this.fee = fee;
    }

    public static int findByDistance(int minDistance) {
        if (minDistance <= DistanceFee.SECTION_TWO.getStartRange()) {
            return BASE_FEE.getFee();
        }
        DistanceFee section = Arrays.stream(DistanceFee.values())
            .filter(value -> minDistance > value.startRange)
            .findFirst()
            .orElse(BASE_FEE);
        int fare = (minDistance - section.getStartRange()) / section.getDivide() * section.getFee();
        return fare + findByDistance(section.getStartRange());

    }

    public int getDivide() {
        return divide;
    }

    public int getFee() {
        return fee;
    }

    public int getStartRange() {
        return startRange;
    }

}
