package edu.nwpu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Stack;

public class Invoker {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Calculator calculator = new Calculator();

        System.out.println("Enter you expression, or 'exit' to quit.");

        boolean keepRunning = true;

        while (keepRunning) {

            String inputString = in.readLine();

            if ("exit".equals(inputString)) {
                keepRunning = false;
            }

            try {
                calculator.eval(inputString);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            DecimalFormat df = new DecimalFormat("0.##########");
            Stack<Double> stack = calculator.getValueStack();

            System.out.print("Stack: ");
            for (Double v : stack) {
                System.out.print(df.format(v) + " ");
            }
            System.out.println();
        }
    }
}
