package com.airwallex.interview;

public class Instruction {
    private Operator operator;
    private Double value;

    public Instruction(Operator operator, Double value) {
        this.operator = operator;
        this.value = value;
    }

    public String getReverseInstruction() throws Exception {
        if (operator.getOperandsNumber() < 1) {
            throw new Exception("Invalid operation for operators.");
        }
        return (operator.getOperandsNumber() < 2) ? String.format("%s", operator.getOpposite()) : String.format("%f %s %f", value, operator.getOpposite(), value);
    }
}
