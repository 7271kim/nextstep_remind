package lotto.model;

import java.util.List;

public class LottoTicket {
    private static final String CHECK_MANUAL = "구매금액 이상으로 manual가격을 입력하셧습니다.";

    public LottoTicket(LottoPrice price, List<Lotto> manual) {
        validate(price, manual);
    }

    private void validate(LottoPrice price, List<Lotto> manual) {
        checkManual(price, manual);
    }

    private void checkManual(LottoPrice price, List<Lotto> manual) {
        if (price.getCount() < manual.size()) {
            throw new IllegalArgumentException(CHECK_MANUAL);
        }
    }

}
