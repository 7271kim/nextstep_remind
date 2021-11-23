package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lotto.constant.Rank;
import lotto.util.GenerateLotto;

public class LottoTicket {

    private static final String CHECK_MANUAL = "구매금액 이상으로 manual가격을 입력하셧습니다.";
    private List<Lotto> ticket = Collections.emptyList();

    private LottoTicket(LottoTicketBuilder builder) {
        validate(builder.price, builder.manual);
        this.ticket = publishTicket(builder.price, builder.manual,
            builder.excludedNumber);
    }

    private List<Lotto> publishTicket(LottoPrice price, List<Lotto> manual,
            Set<Integer> excludedNumber) {
        List<Lotto> autoAndMaunal = new ArrayList<>();
        autoAndMaunal.addAll(manual);
        for (int index = 0; index < price.getCount() - manual.size(); index++) {
            autoAndMaunal.add(Lotto.of(GenerateLotto.create(excludedNumber)));
        }
        return autoAndMaunal;
    }

    private void validate(LottoPrice price, List<Lotto> manual) {
        checkManual(price, manual);
    }

    private void checkManual(LottoPrice price, List<Lotto> manual) {
        if (price.getCount() < manual.size()) {
            throw new IllegalArgumentException(CHECK_MANUAL);
        }
    }

    public List<Lotto> getTicket() {
        return Collections.unmodifiableList(ticket);
    }

    public List<Rank> getWinner(Lotto winLotto, LottoNumber bonus) {
        List<Rank> result = new ArrayList<>();
        for (Lotto lotto : ticket) {
            int matchedCount = lotto.matchedCount(winLotto);
            boolean hasBonus = lotto.contains(bonus);
            result.add(Rank.valueOf(matchedCount, hasBonus));
        }
        Collections.sort(result, (one, two) -> Integer.compare(two.winnerPrice(), one.winnerPrice()));
        return result;
    }

    public static class LottoTicketBuilder {
        private List<Lotto> manual = Collections.emptyList();
        private Set<Integer> excludedNumber = Collections.emptySet();
        private LottoPrice price;

        public LottoTicketBuilder(LottoPrice price) {
            this.price = price;
        }

        public LottoTicketBuilder manual(List<Lotto> manual) {
            this.manual = manual;
            return this;
        }

        public LottoTicketBuilder excludedNumber(Set<Integer> excludedNumber) {
            this.excludedNumber = excludedNumber;
            return this;
        }

        public LottoTicket build() {
            return new LottoTicket(this);
        }
    }
}
