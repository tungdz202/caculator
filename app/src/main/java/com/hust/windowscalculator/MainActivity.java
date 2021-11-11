package com.hust.windowscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnC, btnPlus,
        btnSub, btnDiv, btnMul, btnEqual, btnDot;
    ImageButton btnBackspace;
    TextView textViewNumber, textViewExpression;
    String number = null;
    double firstNumber = 0, lastNumber = 0;
    String status = null;
    boolean operator = false;
    DecimalFormat formatter = new DecimalFormat("######.######");
    String expression, currentNumber;
    boolean dot = true;
    boolean btnCControl = true, btnEqualControl = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);
        btnBackspace = findViewById(R.id.imageButtonBackspace);
        btnC = findViewById(R.id.buttonC);
        btnPlus = findViewById(R.id.buttonPlus);
        btnSub = findViewById(R.id.buttonSub);
        btnDiv = findViewById(R.id.buttonDiv);
        btnMul = findViewById(R.id.buttonMul);
        btnEqual = findViewById(R.id.buttonEqual);
        btnDot = findViewById(R.id.buttonDot);

        textViewNumber = findViewById(R.id.textViewNumber);
        textViewExpression = findViewById(R.id.textViewExpression);

        btn0.setOnClickListener(v -> numberClick("0"));
        btn1.setOnClickListener(v -> numberClick("1"));
        btn2.setOnClickListener(v -> numberClick("2"));
        btn3.setOnClickListener(v -> numberClick("3"));
        btn4.setOnClickListener(v -> numberClick("4"));
        btn5.setOnClickListener(v -> numberClick("5"));
        btn6.setOnClickListener(v -> numberClick("6"));
        btn7.setOnClickListener(v -> numberClick("7"));
        btn8.setOnClickListener(v -> numberClick("8"));
        btn9.setOnClickListener(v -> numberClick("9"));

        btnC.setOnClickListener(v -> {
            number = null;
            status = null;
            textViewNumber.setText("0");
            textViewExpression.setText("");
            firstNumber = 0;
            lastNumber = 0;
            dot = true;
            btnCControl = true;
        });

        btnBackspace.setOnClickListener(v -> {
            if (btnCControl) textViewNumber.setText("0");
            else {
                number = number.substring(0, number.length() - 1);
                if (number.length() == 0) btnBackspace.setClickable(false);
                else dot = !number.contains(".");
                textViewNumber.setText(number);
            }
        });

        btnPlus.setOnClickListener(v -> {
            expression = textViewExpression.getText().toString();
            currentNumber = textViewNumber.getText().toString();
            textViewExpression.setText(expression + currentNumber + "+");

            if (operator) {
                if (status.equals("multiplication")) multiply();
                else if (status.equals("division")) divide();
                else if (status.equals("subtraction")) minus();
                else plus();
            }

            status = "sum";
            operator = false;
            number = null;
        });

        btnSub.setOnClickListener(v -> {
            expression = textViewExpression.getText().toString();
            currentNumber = textViewNumber.getText().toString();
            textViewExpression.setText(expression + currentNumber + "-");

            if (operator) {
                if (status.equals("multiplication")) multiply();
                else if (status.equals("division")) divide();
                else if (status.equals("plus")) plus();
                else minus();
            }

            status = "subtraction";
            operator = false;
            number = null;
        });

        btnMul.setOnClickListener(v -> {
            expression = textViewExpression.getText().toString();
            currentNumber = textViewNumber.getText().toString();
            textViewExpression.setText(expression + currentNumber + "x");

            if (operator) {
                if (status.equals("subtraction")) minus();
                else if (status.equals("division")) divide();
                else if (status.equals("plus")) plus();
                else multiply();
            }

            status = "multiplication";
            operator = false;
            number = null;
        });

        btnDiv.setOnClickListener(v -> {
            expression = textViewExpression.getText().toString();
            currentNumber = textViewNumber.getText().toString();
            textViewExpression.setText(expression + currentNumber + "/");

            if (operator) {
                if (status.equals("subtraction")) minus();
                else if (status.equals("multiplication")) multiply();
                else if (status.equals("plus")) plus();
                else divide();
            }

            status = "division";
            operator = false;
            number = null;
        });

        btnEqual.setOnClickListener(v -> {
            if (operator) {
                if (status.equals("subtraction")) minus();
                else if (status.equals("division")) divide();
                else if (status.equals("plus")) plus();
                else if (status.equals("multiplication")) multiply();
                else firstNumber = Double.parseDouble(textViewNumber.getText().toString());
            }
            operator = false;
            btnEqualControl = true;
        });

        btnDot.setOnClickListener(v -> {
            if (dot) number = number == null ? "0." : number + ".";
            textViewNumber.setText(number);
            dot = false;
        });

    }

    public void numberClick(String view) {
        if (number == null) number = view;
        else if (btnEqualControl) {
            firstNumber = 0;
            lastNumber = 0;
            number = view;
        }
        else number += view;

        textViewNumber.setText(number);
        operator = true;
        btnCControl = false;
        btnBackspace.setClickable(true);
        btnEqualControl = false;
    }

    public void plus() {
        firstNumber += Double.parseDouble(textViewNumber.getText().toString());
        textViewNumber.setText(formatter.format(firstNumber));
        dot = true;
    }

    public void minus() {
        if (firstNumber == 0) {
            firstNumber = Double.parseDouble(textViewNumber.getText().toString());
        }
        else {
            lastNumber = Double.parseDouble(textViewNumber.getText().toString());
            firstNumber = firstNumber - lastNumber;
        }
        textViewNumber.setText(formatter.format(firstNumber));
        dot = true;
    }

    public void multiply() {
        if (firstNumber == 0) firstNumber = 1;
        firstNumber *= Double.parseDouble(textViewNumber.getText().toString());
        textViewNumber.setText(formatter.format(firstNumber));
        dot = true;
    }

    public void divide() {
        lastNumber = Double.parseDouble(textViewNumber.getText().toString());
        firstNumber = firstNumber == 0 ? lastNumber : firstNumber/lastNumber;
        textViewNumber.setText(formatter.format(firstNumber));
        dot = true;
    }

}