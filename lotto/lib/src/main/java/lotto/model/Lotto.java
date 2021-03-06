package lotto.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Lotto {
    private static final String CHECK_VALIDATION = "입력이 정확하지 않습니다. 입력을 확인하세요";
    private Set<LottoNumber> list;

    private Lotto(List<Integer> list) {
        validate(list);
        this.list = convertTo(list);
    }

    private Set<LottoNumber> convertTo(List<Integer> list) {
        Set<LottoNumber> result = new TreeSet<>();
        for (Integer number : list) {
            result.add(new LottoNumber(number));
        }
        return result;

    }

    private void validate(List<Integer> list) {
        checkSize(list);
    }

    private void checkSize(List<Integer> list) {
        if (list == null || list.size() != 6) {
            throw new IllegalArgumentException(CHECK_VALIDATION);
        }
    }

    public static Lotto of(List<Integer> list) {
        return new Lotto(list);
    }

    public static Lotto of(String text) {
        text = text.replace(" ", "");
        return Lotto.of(List.of(text.split(",")).stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList()));
    }

    public boolean contains(LottoNumber lottoNumber) {
        return list.contains(lottoNumber);
    }

    public int matchedCount(Lotto winLotto) {
        return (int)list.stream().filter(winLotto::contains).count();
    }

    public String text() {
        return list.stream()
            .map(number -> String.valueOf(number.getNumber()))
            .collect(Collectors.joining(","));
    }

}
