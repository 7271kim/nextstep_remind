package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.LottoTicket.LottoTicketBuilder;

public class LottoTicketTest {

    @Test
    @DisplayName("정상 생성 확인")
    void createTest() {
        List<Lotto> manual = List.of(Lotto.of("1,2,3,4,5,6"));
        LottoTicket ticket = new LottoTicketBuilder(LottoPrice.of(1000)).manual(manual).build();
        assertThat(ticket).isNotNull();
    }

    @Test
    @DisplayName("가격보다 많게 수동 구매 시 IllegalArgumentException를 발생시킨다.")
    void validateTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<Lotto> manual = List.of(
                Lotto.of("1,2,3,4,5,6"),
                Lotto.of("1,2,3,4,5,7"));
            new LottoTicketBuilder(LottoPrice.of(1000)).manual(manual).build();
        });
    }

    @Test
    @DisplayName("금액에 맞는 로또 번호 생성 확인")
    void createWithLotto() {
        List<Lotto> manual = List.of(Lotto.of("1,2,3,4,5,6"));
        LottoTicket ticket = new LottoTicketBuilder(LottoPrice.of(3000)).build();
        LottoTicket ticketWithManual = new LottoTicketBuilder(LottoPrice.of(3000)).manual(manual).build();
        assertThat(ticket.getTicket().size()).isEqualTo(3);
        assertThat(ticketWithManual.getTicket().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("특정 번호를 제외 후 자동뽑기")
    void createWithEx() {
        List<Lotto> manual = List.of(Lotto.of("1,2,3,4,5,6"));
        Set<Integer> exNumber = Set.of(1, 2, 3, 4, 5);
        LottoTicket ticket = new LottoTicketBuilder(LottoPrice.of(3000))
            .manual(manual)
            .excludedNumber(exNumber)
            .build();
        List<Lotto> lottos = ticket.getTicket();
        assertThat(lottos.size()).isEqualTo(3);
    }

}
