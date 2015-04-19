package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by mauro on 17/04/15.
 */
public class MinusCommand implements Command {

    private final Minus minus;

    public MinusCommand ( Minus min ) {
        this.minus = min;
    }

    @Override
    public double execute() {
        return this.minus.doOperation();
    }

}
