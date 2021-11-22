package lotto.util;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.util.Arrays;
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
        boolean isFail = Arrays.asList(numbers).stream()
            .filter(number -> (int)number == 0)
            .findAny()
            .isPresent();

        assertThat(isFail).isFalse();
    }
}
