package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by mauro on 16/04/15.
 */
public class Minus extends Operator {

    public Minus () {
        this.num1 = this.num2 = new Number();
    }

    @Override
    public double doOperation() {
        return this.num1.getNumber() - this.num2.getNumber();
    }

    @Override
    public Class<MinusCommand> getCommandClass() {
        return MinusCommand.class;
    }

}
