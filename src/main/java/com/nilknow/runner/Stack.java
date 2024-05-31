package com.nilknow.runner;

public class Stack {
    private final StackFrame[] frames;
    private int top; // Index of the topmost frame

    public Stack(int capacity) {
        this.frames = new StackFrame[capacity];
        this.top = -1; // Initially the stack is empty
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == frames.length - 1;
    }

    public void push(StackFrame frame) {
        if (isFull()) {
            // Handle stack overflow (e.g., increase capacity)
            throw new RuntimeException("Stack overflow");
        }
        frames[++top] = frame;
    }

    public StackFrame pop() {
        if (isEmpty()) {
            // Handle stack underflow
            throw new RuntimeException("Stack underflow");
        }
        return frames[top--];
    }

    public StackFrame peek() {
        if (isEmpty()) {
            return null; // Or throw an exception
        }
        return frames[top];
    }
}