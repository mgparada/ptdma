package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by root on 17/04/15.
 */
public class Divide extends Operator {

    public Divide () {
        this.num1 = this.num2 = new Number();
    }

    /* Always op1 / op2 */
    @Override
    public double doOperation () {
        if( num2.getNumber() > 0 )
            return (double)num1.getNumber() / (double)num2.getNumber();

        return 0.0;
    }

    @Override
    public Class<DivideCommand> getCommandClass() {
        return DivideCommand.class;
    }
}
