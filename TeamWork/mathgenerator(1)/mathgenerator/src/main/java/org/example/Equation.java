package org.example;

import java.util.Random;
import java.util.Stack;

public class Equation {
    private static final String[] SYMBOLS = {"+", "-", "×", "÷"};
    private final String infixExpression;
    private final String postfixExpression;
    private final Fraction result;

    // 用于解析表达式的构造函数
    public Equation(String expression) {
        this.infixExpression = expression.replaceAll(" ", "");
        this.postfixExpression = infixToPostfix(this.infixExpression);
        this.result = calculatePostfix(this.postfixExpression);
    }

    // 随机生成表达式的构造函数
    public Equation(int numSymbols, int max) {
        StringBuilder expression = new StringBuilder();
        Random random = new Random();

        int firstNumber = random.nextInt(max) + 1; // 第一个数值
        expression.append(firstNumber); // 加入第一个数值

        // 随机生成后续的运算符和数值
        for (int i = 0; i < numSymbols; i++) {
            String symbol = SYMBOLS[random.nextInt(SYMBOLS.length)];
            int nextNumber = random.nextInt(max) + 1; // 生成下一个数值

            // 确保不会导致负数
            if (symbol.equals("-")) {
                // 检查前一个数是否大于下一个数，避免负数
                if (firstNumber < nextNumber) {
                    // 若当前数小于下一个数，则不使用减法
                    continue;
                }
            }

            expression.append(" ").append(symbol).append(" ").append(nextNumber);
            firstNumber = calculateIntermediateResult(firstNumber, symbol, nextNumber); // 更新当前数值
        }

        this.infixExpression = expression.toString();
        this.postfixExpression = infixToPostfix(this.infixExpression);
        this.result = calculatePostfix(this.postfixExpression);
    }
    private int calculateIntermediateResult(int a, String operator, int b) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "×":
                return a * b;
            case "÷":
                return a / b; // 假设除数不为0
            default:
                return a;
        }
    }



    public String getInfixExpression() {
        return infixExpression;
    }

    public String getPostfixExpression() {
        return postfixExpression;
    }

    public Fraction getResult() {
        return result;
    }

    private boolean isSymbol(String s) {
        for (String symbol : SYMBOLS) {
            if (s.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    private int symbolPriority(String s) {
        if (s.equals("+") || s.equals("-")) {
            return 1;
        } else if (s.equals("×") || s.equals("÷")) {
            return 2;
        }
        return 0;
    }

    private String infixToPostfix(String infixExpression) {
        StringBuilder result = new StringBuilder();
        Stack<String> stack = new Stack<>();
        String[] tokens = infixExpression.split("(?=[-+×÷()])|(?<=[-+×÷()])");

        for (String token : tokens) {
            if (!isSymbol(token) && !token.isEmpty()) {
                result.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // 出栈左括号
                }
            } else if (isSymbol(token)) {
                while (!stack.isEmpty() && symbolPriority(stack.peek()) >= symbolPriority(token)) {
                    result.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }
        return result.toString().trim(); // 去除多余空格
    }

    private Fraction calculatePostfix(String expression) {
        String[] tokens = expression.split(" ");
        Stack<Fraction> stack = new Stack<>();

        for (String token : tokens) {
            if (!isSymbol(token) && !token.isEmpty()) {
                stack.push(new Fraction(token));
            } else if (isSymbol(token)) {
                if (stack.size() < 2) {
                    throw new RuntimeException("后缀表达式异常");
                }
                Fraction b = stack.pop();
                Fraction a = stack.pop();
                stack.push(Fraction.operation(token, a, b));
            }
        }
        return stack.pop();
    }
}
