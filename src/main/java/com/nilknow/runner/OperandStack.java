package com.nilknow.runner;

public class OperandStack {

    private final Object[] stack;
    private int top; // Index of the topmost element

    public OperandStack(int capacity) {
        this.stack = new Object[capacity];
        this.top = -1;
    }

    public void push(Object value) {
        if (isFull()) {
            throw new RuntimeException("Operand stack overflow");
        }
        stack[++top] = value;
    }

    public Object pop() {
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
}
