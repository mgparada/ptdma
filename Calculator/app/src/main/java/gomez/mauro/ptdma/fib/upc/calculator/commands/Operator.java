package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by root on 17/04/15.
 */
public abstract class Operator {

    protected Number num1, num2;

    public Operator () {
        this.num1 = new Number();
        this.num2 = new Number();
    }

    public Operator ( Number n1, Number n2 ) {
        this.num1 = n1;
        this.num2 = n2;
    }

    public void setNumbers ( Number n1, Number n2 ) {
        this.num1 = n1;
        this.num2 = n2;
    }

    public abstract double doOperation();

    public abstract Class<?> getCommandClass();

}
