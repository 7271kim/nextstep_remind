package lotto.constant;

public enum Message {
    BUY_TEXT("구입금액을 입력해 주세요."),
    MANUAL_TEXT("수동으로 구매할 로또 수를 입력해 주세요."),
    MANUAL_REPEAT_TEXT("수동으로 구매할 번호를 입력해 주세요.(,로 구분합니다.)"),
    ONLY_NUMBER_TEXT("숫자만 입력 가능합니다."),
    HOW_BUY_TEXT("수동으로 %d장, 자동으로 %d개를 구매했습니다."),
    WINNER_TEXT("지난 주 당첨 번호를 입력해 주세요.( , 구분자로 하여 입력하세요)"),
    BONUS_TEXT("보너스 번호를 입력하세요"),
    EXCLUDED_TEXT("%s 번호 제외되었습니다."),
    SPLIT_TEXT("[%s]"),
    STATIC_TEXT("%d개 일치 (%d원)- %d개"),
    STATIC_SECOND_TEXT("%d개 일치, 보너스 볼 일치 (%d원)- %d개"),
    EXCEPT_TEXT("자동에 노출되고 싶지 않은 번호를 , 구분자로 하여 입력하세요");

    private String text;

    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
