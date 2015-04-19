package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by mauro on 16/04/15.
 */
public class SumCommand implements Command {

    private final Sum sum;

    public SumCommand( Sum sum ) {
        this.sum = sum;
    }

    @Override
    public double execute () {
        return this.sum.doOperation();
    }
}
