package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lotto.constant.Rank;
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

    @ParameterizedTest
    @DisplayName("당첨 번호를 받고 Rank 확인")
    @MethodSource("rankData")
    void rankTest(Lotto lotto, Rank rank) {
        Lotto winLotto = Lotto.of("1, 2, 3, 4, 5, 6");
        LottoNumber bonus = new LottoNumber(7);
        List<Lotto> manual = List.of(lotto, Lotto.of("1, 2, 3, 4, 5, 6"));
        LottoTicket ticket = new LottoTicketBuilder(LottoPrice.of(2000))
            .manual(manual)
            .build();
        List<Rank> winnerList = ticket.getWinner(winLotto, bonus);
        assertThat(winnerList.get(0)).isEqualTo(Rank.FIRST);
        assertThat(winnerList.get(1)).isEqualTo(rank);

    }

    @Test
    @DisplayName("통계 추출 데이터 확인")
    void allStaticTest() {
        List<Lotto> manual = List.of(Lotto.of("1,2,3,4,5,6"), Lotto.of("1,2,3,4,5,6"), Lotto.of("1,2,3,4,5,7"), Lotto.of("2,3,4,5,6,9"));
        Lotto winner = Lotto.of("1,2,3,4,5,6");
        LottoNumber bonus = new LottoNumber(7);
        LottoTicket ticket = new LottoTicketBuilder(LottoPrice.of(4000)).manual(manual).build();
        Map<Rank, Long> allStatic = ticket.allStatic(winner, bonus);
        assertThat(allStatic)
            .containsEntry(Rank.FIRST, 2l)
            .containsEntry(Rank.SECOND, 1l)
            .containsEntry(Rank.THIRD, 1l);
    }

    private static Stream<Arguments> rankData() {
        return Stream.of(
            Arguments.of(Lotto.of("1, 2, 3, 4, 5, 6"), Rank.FIRST),
            Arguments.of(Lotto.of("1, 2, 3, 4, 5, 7"), Rank.SECOND),
            Arguments.of(Lotto.of("2, 3, 4, 5, 6, 9"), Rank.THIRD),
            Arguments.of(Lotto.of("3, 4, 5, 6, 7, 8"), Rank.FOURTH),
            Arguments.of(Lotto.of("4, 5, 6, 7, 8, 9"), Rank.FIFTH),
            Arguments.of(Lotto.of("4, 5, 6, 7, 8, 9"), Rank.FIFTH),
            Arguments.of(Lotto.of("11, 12, 13, 14, 15, 16"), Rank.NONE));
    }

}
