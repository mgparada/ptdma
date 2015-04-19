package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by mauro on 16/04/15.
 */
public class DivideCommand implements Command {

    private final Divide divide;

    public DivideCommand(Divide d) {
        this.divide = d;
    }

    @Override
    public double execute () {
        return this.divide.doOperation();
    }
}
