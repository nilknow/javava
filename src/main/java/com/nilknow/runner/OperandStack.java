package com.nilknow.runner;

public class OperandStack {

    private final int[] stack;
    private int top; // Index of the topmost element

    public OperandStack(int capacity) {
        this.stack = new int[capacity];
        this.top = -1;
    }

    public void push(int value) {
        if (isFull()) {
            throw new RuntimeException("Operand stack overflow");
        }
        stack[++top] = value;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Operand stack underflow");
        }
        return stack[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == stack.length - 1;
    }

    // Simulated bytecode instructions (for demonstration purposes only)
    public void iconst(int value) {
        push(value); // Push a constant value onto the stack
    }

    public int iadd() {
        if (top < 1) {
            throw new RuntimeException("Insufficient operands for addition");
        }
        int operand2 = pop();
        int operand1 = pop();
        int result = operand1 + operand2;
        push(result); // Push the addition result onto the stack
        return result;
    }
}
