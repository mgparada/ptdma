package gomez.mauro.ptdma.fib.upc.calculator.commands;

/**
 * Created by mauro on 17/04/15.
 */
public class Number {

    private double number;

    public Number () { this.number = 0.0; }

    public Number ( double num ) {
        this.number = num;
    }

    public int getNumber () {
        return new Double(this.number).intValue();
    }

    @Override
    public String toString () {
        return String.valueOf(this.number);
    }
}
