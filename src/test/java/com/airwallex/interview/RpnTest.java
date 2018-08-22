package com.airwallex.interview;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Stack;

public class RpnTest {
    private Calculator calculator;

    @BeforeMethod
    public void init() {
        calculator = new Calculator();
    }

    public Stack<Double> makeStack(double... args) {
        Stack<Double> result = new Stack<>();
        for (double d : args) {
            result.push(d);
        }
        return result;
    }

    public void invoke(String input, Stack<Double> expectedResult) throws Exception {
        calculator.eval(input);
        assertEquals(expectedResult, calculator.getValueStack());
    }

    @Test
    public void case01() throws Exception {
        invoke("5 2", makeStack(5, 2));
    }

    @Test
    public void case02() throws Exception {
        invoke("2 sqrt", makeStack(1.4142135623730951));
        invoke("clear 9 sqrt", makeStack(3));
    }

    @Test
    public void case03() throws Exception {
        invoke("5 2 -", makeStack(3));
        invoke("3 -", makeStack(0));
        invoke("clear", makeStack());
    }

    @Test
    public void case04() throws Exception {
        invoke("5 4 3 2", makeStack(5, 4, 3, 2));
        invoke("undo undo *", makeStack(20));
        invoke("5 *", makeStack(100));
        invoke("undo", makeStack(20, 5));
    }

    @Test
    public void case05() throws Exception {
        invoke("7 12 2 /", makeStack(7, 6));
        invoke("*", makeStack(42));
        invoke("4 /", makeStack(10.5));
    }

    @Test
    public void case06() throws Exception {
        invoke("1 2 3 4 5", makeStack(1, 2, 3, 4, 5));
        invoke("*", makeStack(1, 2, 3, 20));
        invoke("clear 3 4 -", makeStack(-1));
    }

    @Test
    public void case07() throws Exception {
        invoke("1 2 3 4 5", makeStack(1, 2, 3, 4, 5));
        invoke("* * * *", makeStack(120));
    }

    @Test(expectedExceptions = Exception.class)
    public void case08() throws Exception {
        invoke("1 2 3 * 5 + * * 6 5", makeStack(11));
    }
}
