import io.Input;
import io.Output;
import io.impl.FileInput;
import io.impl.FileOutput;
import lotto.game.Start;

public class Main {

    public static void main(String[] args) {
        //Input io = new ConsoleInput();
        //Output out = new ConsoleOutput();
        Input io = new FileInput("src/main/resources/input.txt");
        Output out = new FileOutput("src/main/resources/output.txt");
        Start lotto = new Start(io, out);
        lotto.startLotto();
        io.close();
        out.close();
    }

}
