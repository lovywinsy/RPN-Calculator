package com.airwallex.interview;

import java.util.Stack;

public class Calculator {
    private Stack<Double> valueStack = new Stack<>();
    private Stack<Instruction> instructionsStack = new Stack<>();
    private int currentIndex;

    private Double tryParseDouble(String origin) {
        try {
            return Double.parseDouble(origin);
        } catch (Exception e) {
            return null;
        }
    }

    public void eval(String input) throws Exception {
        eval(input, false);
    }

    private void eval(String input, boolean isUndoOperation) throws Exception {
        if (null == input) {
            throw new Exception("Input cannot be null.");
        }
        currentIndex = 0;
        String[] result = input.split("\\s");
        for (String s : result) {
            ++currentIndex;
            processToken(s, isUndoOperation);
        }
    }

    private void processToken(String token, boolean isUndoOperation) throws Exception {
        Double value = tryParseDouble(token);
        if (null == value) {
            processOperator(token, isUndoOperation);
        } else {
            valueStack.push(value);
            if (!isUndoOperation) {
                instructionsStack.push(null);
            }
        }
    }

    private void processOperator(String operatorString, boolean isUndoOperator) throws Exception {
        if (valueStack.isEmpty()) {
            throw new Exception("Empty stack.");
        }

        Operator operator = Operator.getEnum(operatorString);

        if (null == operator) {
            throw new Exception("Invalid operator");
        }

        if (Operator.CLEAR == operator) {
            clearStacks();
            return;
        }

        if (Operator.UNDO == operator) {
            undoLastInstruction();
            return;
        }

        if (operator.getOperandsNumber() > valueStack.size()) {
            throw new Exception(String.format("operator %s (position: %d): insufficient parameters", operator, currentIndex));
        }

        Double firstOperand = valueStack.pop();
        Double secondOperand = operator.getOperandsNumber() > 1 ? valueStack.pop() : null;

        Double result = operator.calculate(firstOperand, secondOperand);

        if (null != result) {
            valueStack.push(result);
            if (!isUndoOperator) {
                instructionsStack.push(new Instruction(Operator.getEnum(operatorString), firstOperand));
            }
        }
    }

    private void clearStacks() {
        valueStack.clear();
        instructionsStack.clear();
    }

    private void undoLastInstruction() throws Exception {
        if (instructionsStack.isEmpty()) {
            throw new Exception();
        }

        Instruction lastInstruction = instructionsStack.pop();
        if (null == lastInstruction) {
            valueStack.pop();
        } else {
            eval(lastInstruction.getReverseInstruction(), true);
        }
    }

    public Stack<Double> getValueStack() {
        return valueStack;
    }
}
