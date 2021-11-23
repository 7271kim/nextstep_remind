package lotto.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.Input;
import io.Output;
import lotto.constant.Message;
import lotto.constant.Rank;
import lotto.model.Lotto;
import lotto.model.LottoNumber;
import lotto.model.LottoPrice;
import lotto.model.LottoTicket;
import lotto.model.LottoTicket.LottoTicketBuilder;

public class Start {
    Input input;
    Output out;

    public Start(Input input, Output out) {
        this.input = input;
        this.out = out;
    }

    public void startLotto() {
        LottoPrice price = getBuyData();
        int count = getManualCount();
        List<Lotto> manul = getManualLotto(count);
        Set<Integer> except = getExceptNumber();
        LottoTicket ticket = new LottoTicketBuilder(price)
            .manual(manul)
            .excludedNumber(except)
            .build();
        showList(ticket);
        showWinners(winner(ticket));
    }

    private void showWinners(Map<Rank, Long> winners) {
        out.show(String.format(Message.STATIC_TEXT.getText(), Rank.FIRST.matchedCount(), Rank.FIRST.winnerPrice(), winners.getOrDefault(Rank.FIRST, 0l)));
        out.show(String.format(Message.STATIC_SECOND_TEXT.getText(), Rank.SECOND.matchedCount(), Rank.SECOND.winnerPrice(), winners.getOrDefault(Rank.SECOND, 0l)));
        out.show(String.format(Message.STATIC_TEXT.getText(), Rank.THIRD.matchedCount(), Rank.THIRD.winnerPrice(), winners.getOrDefault(Rank.THIRD, 0l)));
        out.show(String.format(Message.STATIC_TEXT.getText(), Rank.FOURTH.matchedCount(), Rank.FOURTH.winnerPrice(), winners.getOrDefault(Rank.FOURTH, 0l)));
        out.show(String.format(Message.STATIC_TEXT.getText(), Rank.FIFTH.matchedCount(), Rank.FIFTH.winnerPrice(), winners.getOrDefault(Rank.FIFTH, 0l)));
    }

    private Map<Rank, Long> winner(LottoTicket ticket) {
        out.show("");
        try {
            out.show(Message.WINNER_TEXT.getText());
            Lotto winner = Lotto.of(input.accept());
            out.show(Message.BONUS_TEXT.getText());
            LottoNumber bonus = new LottoNumber(input.accept());
            return ticket.allStatic(winner, bonus);
        } catch (Exception error) {
            out.show(error.getMessage());
            return winner(ticket);
        }
    }

    private void showList(LottoTicket ticket) {
        out.show("");
        out.show(String.format(Message.HOW_BUY_TEXT.getText(), ticket.getManualCount(), ticket.getAutoCount()));
        out.show(String.format(Message.EXCLUDED_TEXT.getText(), ticket.getExcludedNumber()));
        List<Lotto> list = ticket.getTicket();
        for (int index = 0; index < list.size(); index++) {
            out.show(String.format(Message.SPLIT_TEXT.getText(), list.get(index).text()));
        }
    }

    private Set<Integer> getExceptNumber() {
        out.show("");
        try {
            out.show(Message.EXCEPT_TEXT.getText());
            return Arrays.stream(input.accept().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        } catch (Exception error) {
            out.show(error.getMessage());
            return getExceptNumber();
        }
    }

    private List<Lotto> getManualLotto(int count) {
        out.show("");
        try {
            List<Lotto> manual = new ArrayList<>();
            out.show(Message.MANUAL_REPEAT_TEXT.getText());
            for (int index = 0; index < count; index++) {
                String text = input.accept();
                manual.add(Lotto.of(text));
            }
            return manual;
        } catch (Exception error) {
            out.show(error.getMessage());
            return getManualLotto(count);
        }
    }

    private int getManualCount() {
        out.show("");
        try {
            out.show(Message.MANUAL_TEXT.getText());
            return Integer.parseInt(input.accept());
        } catch (Exception error) {
            out.show(Message.ONLY_NUMBER_TEXT.getText());
            return getManualCount();
        }
    }

    private LottoPrice getBuyData() {
        out.show("");
        try {
            out.show(Message.BUY_TEXT.getText());
            return LottoPrice.of(input.accept());
        } catch (Exception error) {
            out.show(error.getMessage());
            return getBuyData();
        }
    }

}
