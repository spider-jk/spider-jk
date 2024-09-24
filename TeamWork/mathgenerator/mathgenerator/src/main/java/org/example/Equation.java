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

        // 随机生成数值和符号
        for (int i = 0; i < numSymbols; i++) {
            if (i > 0) {
                expression.append(SYMBOLS[random.nextInt(SYMBOLS.length)]).append(" ");
            }
            expression.append(random.nextInt(max) + 1); // 生成 1 到 max 的随机数
        }
        this.infixExpression = expression.toString().trim(); // 去除多余空格
        this.postfixExpression = infixToPostfix(this.infixExpression);
        this.result = calculatePostfix(this.postfixExpression);
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
