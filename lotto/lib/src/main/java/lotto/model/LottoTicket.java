package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lotto.util.GenerateLotto;

public class LottoTicket {
    private static final String CHECK_MANUAL = "구매금액 이상으로 manual가격을 입력하셧습니다.";
    private List<Lotto> ticket;

    public LottoTicket(LottoPrice price, List<Lotto> manual) {
        validate(price, manual);
        this.ticket = publishTicket(price, manual);
    }

    private List<Lotto> publishTicket(LottoPrice price, List<Lotto> manual) {
        List<Lotto> autoAndMaunal = new ArrayList<>();
        autoAndMaunal.addAll(manual);
        for (int index = 0; index < price.getCount() - manual.size(); index++) {
            autoAndMaunal.add(Lotto.of(GenerateLotto.create()));
        }
        return autoAndMaunal;
    }

    public LottoTicket(LottoPrice price) {
        this(price, Collections.emptyList());
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

}
