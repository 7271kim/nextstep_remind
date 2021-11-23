import io.Input;
import io.Output;
import io.impl.InputConsole;
import io.impl.OutputConsole;
import lotto.game.Start;

public class Main {

    public static void main(String[] args) {
        Input io = new InputConsole();
        Output out = new OutputConsole();
        Start lotto = new Start(io, out);
        lotto.startLotto();
    }

}
