package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoNumberTest {

    @ParameterizedTest
    @DisplayName("잘못된 입력 시,IllegalArgumentException을 발생시킨다.")
    @ValueSource(ints = {0, 46})
    void validationTest(int input) {
        assertThrows(IllegalArgumentException.class, () -> {
            new LottoNumber(input);
        });
    }

    @Test
    @DisplayName("LottoNuber 정상 반환 확인")
    void getNuberTest() {
        LottoNumber number = new LottoNumber(2);
        assertThat(number.getNumber()).isEqualTo(2);
    }

}
