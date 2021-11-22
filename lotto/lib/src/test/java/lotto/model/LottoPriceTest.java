package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoPriceTest {

    @ParameterizedTest
    @DisplayName("정상 생성 테스트")
    @ValueSource(ints = {1000, 10000})
    void createTest(int input) {
        LottoPrice ticket = new LottoPrice(input);
        assertThat(ticket).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("1000단위가 아닐 시 IllegalArgumentException을 발생시킨다")
    @ValueSource(ints = {1, 1100, 100, 0})
    void validateTest(int input) {
        assertThrows(IllegalArgumentException.class, () -> {
            new LottoPrice(input);
        });
    }

    @Test
    @DisplayName("가격당 횟수 확인")
    void countTest() {
        int money = 2000;
        LottoPrice ticket = new LottoPrice(money);
        int count = ticket.getCount();
        assertThat(count).isEqualTo(money / ticket.getLottoPrice());
    }

}
