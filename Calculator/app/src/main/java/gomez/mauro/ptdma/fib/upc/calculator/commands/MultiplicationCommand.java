package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by root on 16/04/15.
 */
public class MultiplicationCommand implements Command {

    private final Multiplication multiplication;

    public MultiplicationCommand( Multiplication multi ) {
        this.multiplication = multi;
    }

    @Override
    public double execute () {
        return this.multiplication.doOperation();
    }
}
