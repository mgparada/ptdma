package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by mauro on 16/04/15.
 */
public class Multiplication extends Operator {

    public Multiplication () {
        this.num1 = this.num2 = new Number();
    }

    @Override
    public double doOperation () {
        return this.num1.getNumber() * this.num2.getNumber();
    }

    @Override
    public Class<MultiplicationCommand> getCommandClass() {
        return MultiplicationCommand.class;
    }
}
