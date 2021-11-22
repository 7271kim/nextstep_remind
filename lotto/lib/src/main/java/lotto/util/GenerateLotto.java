package lotto.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateLotto {

    private static final int START = 1;
    private static final int END = 45;
    private static final int LENGTH = 6;

    private static final List<Integer> LOTTO_RANGE = IntStream
        .rangeClosed(START, END).boxed()
        .collect(Collectors.toList());

    public static List<Integer> create() {
        Collections.shuffle(LOTTO_RANGE);
        return new ArrayList<>(LOTTO_RANGE.subList(0, LENGTH));
    }

}
