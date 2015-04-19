package gomez.mauro.ptdma.fib.upc.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import gomez.mauro.ptdma.fib.upc.calculator.commands.*;
import gomez.mauro.ptdma.fib.upc.calculator.commands.Number;


public class Calculator extends Activity {

    /* Buttons for numbers */
    private Button button0, button1, button2, button3, button4, button5, button6, button7;
    private Button button8, button9;

    /* Buttons for operations */
    private Button btnPlus, btnMinus, btnMult, btnDivide, btnClear, btnEqual;

    /* TextView for result */
    private TextView tvResult;

    /* Listener */
    private final NumberListener numberListener = new NumberListener();
    private final OperatorListener operatorListener = new OperatorListener();

    /* Operations */
    ArrayList<Object> toCalculate = new ArrayList<Object>();

    /* Operation flag */
    boolean isCalculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvResult = (TextView) findViewById(R.id.tvResult);

        initializeButtons();
        setListeners();

        btnClear.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResult.setText("");
                toCalculate.clear();
            }
        });

        btnEqual.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op1 = new String();
                String op2 = new String();
                Operator operator = null;
                Command commandOperation = null;
                for (Object o : toCalculate) {
                    if( o instanceof Number ) {
                        if( operator == null )
                            op1 = op1.concat( Integer.valueOf(((Number) o).getNumber()).toString() );
                        else
                            op2 = op2.concat( Integer.valueOf(((Number) o).getNumber()).toString() );
                    }else if( o instanceof Operator ) {
                        operator = (Operator) o;
                    }
                }

                if( op1.length() <= 0 || op2.length() <= 0 ) {
                    Toast.makeText(getApplicationContext(), "Malformed Expression", Toast.LENGTH_LONG).show();
                    return;
                }

                operator.setNumbers( new Number(Double.parseDouble(op1)), new Number(Double.parseDouble(op2)) );

                Class<?> clazz = operator.getCommandClass();
                try{
                    Class commandClazz = Class.forName(clazz.getName());
                    Constructor cons = commandClazz.getConstructor(operator.getClass());
                    commandOperation = (Command) cons.newInstance( operator );
                }catch (Exception e) {
                    e.printStackTrace();
                }

                double result = commandOperation.execute();
                tvResult.setText(String.valueOf(result));

                isCalculated = true;
                toCalculate.clear();
            }
        });

    }

    private void initializeButtons () {
        button0 = (Button) findViewById(R.id.bt0);
        button1 = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        button3 = (Button) findViewById(R.id.bt3);
        button4 = (Button) findViewById(R.id.bt4);
        button5 = (Button) findViewById(R.id.bt5);
        button6 = (Button) findViewById(R.id.bt6);
        button7 = (Button) findViewById(R.id.bt7);
        button8 = (Button) findViewById(R.id.bt8);
        button9 = (Button) findViewById(R.id.bt9);

        btnClear = (Button) findViewById(R.id.btclear);
        btnDivide = (Button) findViewById(R.id.btdiv);
        btnEqual = (Button) findViewById(R.id.btequals);
        btnMinus = (Button) findViewById(R.id.btminus);
        btnMult = (Button) findViewById(R.id.btmult);
        btnPlus = (Button) findViewById(R.id.btplus);
    }

    private void setListeners() {
        button0.setOnClickListener(numberListener);
        button1.setOnClickListener(numberListener);
        button2.setOnClickListener(numberListener);
        button3.setOnClickListener(numberListener);
        button4.setOnClickListener(numberListener);
        button5.setOnClickListener(numberListener);
        button6.setOnClickListener(numberListener);
        button7.setOnClickListener(numberListener);
        button8.setOnClickListener(numberListener);
        button9.setOnClickListener(numberListener);

        btnDivide.setOnClickListener(operatorListener);
        btnMinus.setOnClickListener(operatorListener);
        btnMult.setOnClickListener(operatorListener);
        btnPlus.setOnClickListener(operatorListener);
    }

    private class NumberListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if( isCalculated ) {
                isCalculated = false;
                tvResult.setText("");
            }
            Double number = Double.parseDouble(((Button) findViewById(v.getId())).getText().toString());
            toCalculate.add( (Object) new Number(number) );
            tvResult.setText(TextUtils.concat(tvResult.getText(), ((Button)findViewById(v.getId())).getText()) );
        }
    }

    private class OperatorListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if( isCalculated ) {
                isCalculated = false;
                tvResult.setText("");
            }
            String operator = ((Button)findViewById(v.getId())).getText().toString();
            Class<Operator> op = Operator.class;
            if( !checkIfContainClass( toCalculate, Operator.class ) ) {
                toCalculate.add( (Object) createOperator(operator) );
                tvResult.setText(TextUtils.concat(tvResult.getText(), ((Button)findViewById(v.getId())).getText()) );
            }
        }

        public Operator createOperator ( String op ) {
            switch ( op ) {
                case "+":
                    return (Operator) new Sum();
                case "-":
                    return (Operator) new Minus();
                case "*":
                    return (Operator) new Multiplication();
                case "/":
                    return (Operator) new Divide();
                default:
                    return null;
            }
        }

        public boolean checkIfContainClass ( ArrayList<Object> arrL, Class clazz) {
            for( Object o: arrL )
                if( o != null && clazz.isAssignableFrom(o.getClass()) )
                    return true;

            return false;
        }
    }
}
