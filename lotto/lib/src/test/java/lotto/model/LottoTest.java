package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoTest {

    @Test
    @DisplayName("로또 정상 생성 확인 테스트")
    void createTest() {
        Lotto lotto = Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(lotto).isNotNull();
    }

    @Test
    @DisplayName("배열이 6개의 숫자가 안될 시, 범위 밖의 숫자 입력 시 IllegalArgumentException을 반환한다.")
    void validateTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Lotto.of(Arrays.asList(1));
            Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
            Lotto.of(Arrays.asList(0, 2, 3, 4, 5, 6));
            Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 47));
        });
    }
}
