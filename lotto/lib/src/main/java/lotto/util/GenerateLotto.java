package lotto.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateLotto {

    private static final int START = 1;
    private static final int END = 45;
    private static final int LENGTH = 6;
    private static final String CHECK_EXCLUDEDNUMBER = String
        .format("최대 %d개수 만큼 제외 가능합니다", END - LENGTH);

    private static final List<Integer> LOTTO_RANGE = IntStream
        .rangeClosed(START, END).boxed()
        .collect(Collectors.toList());

    public static List<Integer> create() {
        Collections.shuffle(LOTTO_RANGE);
        return new ArrayList<>(LOTTO_RANGE.subList(0, LENGTH));
    }

    public static List<Integer> create(Set<Integer> excludedNumber) {
        if (excludedNumber == null || excludedNumber.size() > END - LENGTH) {
            throw new IllegalArgumentException(CHECK_EXCLUDEDNUMBER);
        }
        List<Integer> excludedRange = limitRange(excludedNumber);
        return new ArrayList<>(excludedRange.subList(0, LENGTH));
    }

    private static List<Integer> limitRange(Set<Integer> excludedNumber) {
        Collections.shuffle(LOTTO_RANGE);
        return LOTTO_RANGE.stream()
            .filter(number -> excludedNumber.stream()
                .noneMatch(number::equals))
            .collect(Collectors.toList());
    }

}
