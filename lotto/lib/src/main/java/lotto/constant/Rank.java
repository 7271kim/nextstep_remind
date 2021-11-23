package lotto.constant;

import java.util.Arrays;

public enum Rank {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000, true),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    NONE(0, 0);

    private Integer matchedCount;
    private Integer winnerPrice;
    private boolean hasBonus;

    private Rank(Integer matchedCount, Integer winnerPrice) {
        this(matchedCount, winnerPrice, false);
    }

    private Rank(Integer matchedCount, Integer winnerPrice, boolean hasBonus) {
        this.matchedCount = matchedCount;
        this.winnerPrice = winnerPrice;
        this.hasBonus = hasBonus;
    }

    public Integer matchedCount() {
        return matchedCount;
    }

    public Integer winnerPrice() {
        return winnerPrice;
    }

    public static Rank valueOf(int matchedCount, boolean matchedBonus) {
        return Arrays.stream(values())
            .filter(rank -> rank.matched(matchedCount, matchedBonus))
            .findFirst()
            .orElse(NONE);
    }

    private boolean matched(int matchedCount, boolean matchedBonus) {
        if (this.hasBonus) {
            return this.matchedCount == matchedCount && matchedBonus;
        }
        return this.matchedCount == matchedCount;
    }
}
