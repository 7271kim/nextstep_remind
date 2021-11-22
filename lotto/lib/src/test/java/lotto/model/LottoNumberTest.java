package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoNumberTest {

    @ParameterizedTest
    @DisplayName("1-45이외의 숫자 입력 시,IllegalArgumentException을 발생시킨다.")
    @ValueSource(ints = {0, 46})
    void validationTest(int input) {
        assertThrows(IllegalArgumentException.class, () -> {
            new LottoNumber(input);
        });
    }

    @ParameterizedTest
    @DisplayName("LottoNuber 정상 반환 확인")
    @ValueSource(ints = {1, 2, 45})
    void getNuberTest(int input) {
        LottoNumber number = new LottoNumber(input);
        assertThat(number.getNumber()).isEqualTo(input);
    }

}
