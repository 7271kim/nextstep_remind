package lotto.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class GenerateLottoTest {

    @RepeatedTest(100)
    @DisplayName("1~45의 중복되지 않는 로또 번호 생성 테스트")
    void 로또번호생성_테스트() {
        List<Integer> lotto = GenerateLotto.create();
        boolean isSafe = true;

        for (int index = 0; index < lotto.size() && isSafe; index++) {
            int number = lotto.get(index);
            isSafe = 1 <= number && number <= 45;
        }

        assertThat(isSafe).isTrue();

    }

    @Test
    @DisplayName("1부터 45사이 숫자가 존재하는지 확인하는 테스트, 500번 반복 시 최소 1개 씩은 존재해야 한다.")
    void 로또번호_존재여부_테스트() {
        int[] numbers = new int[45];
        for (int loop = 0; loop < 500; loop++) {
            List<Integer> lotto = GenerateLotto.create();
            for (int index = 0; index < lotto.size(); index++) {
                numbers[lotto.get(index) - 1] = 1;
            }
        }
        boolean isFail = Arrays.stream(numbers)
            .filter(number -> number == 0)
            .findAny()
            .isPresent();

        assertThat(isFail).isFalse();
    }

    @Test
    @DisplayName("특정 번호가 제외되고 뽑혓는지 확인")
    void 특정번호가_제외되었는지_테스트() {
        int[] numbers = new int[45];
        Set<Integer> excludedNumber = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17);
        for (int loop = 0; loop < 500; loop++) {
            List<Integer> lotto = GenerateLotto.create(excludedNumber);
            for (int index = 0; index < lotto.size(); index++) {
                numbers[lotto.get(index) - 1] = 1;
            }
        }
        boolean isFail = excludedNumber.stream()
            .filter(number -> numbers[number - 1] == 1)
            .findAny()
            .isPresent();

        assertThat(isFail).isFalse();
    }

    @Test
    @DisplayName("40개 이상의 번호를 제외하고 로또구매는 불가능하다.")
    void 특정번호가_제외_유효성_테스트() {
        assertThrows(IllegalArgumentException.class, () -> {
            Set<Integer> excludedNumber = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40);
            GenerateLotto.create(excludedNumber);
        });
    }
}
