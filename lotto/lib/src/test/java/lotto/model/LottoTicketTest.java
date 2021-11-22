package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoTicketTest {

    @Test
    @DisplayName("정상 생성 확인")
    void createTest() {
        List<Lotto> manual = Arrays.asList(Lotto.of("1,2,3,4,5,6"));
        LottoTicket ticket = new LottoTicket(LottoPrice.of(1000), manual);
        assertThat(ticket).isNotNull();
    }

    @Test
    @DisplayName("가격보다 많게 수동 구매 시 IllegalArgumentException를 발생시킨다.")
    void validateTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<Lotto> manual = Arrays.asList(
                Lotto.of("1,2,3,4,5,6"),
                Lotto.of("1,2,3,4,5,7"));
            new LottoTicket(LottoPrice.of(1000), manual);
        });
    }

}
