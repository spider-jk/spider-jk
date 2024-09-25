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

    public Equation(int numSymbols, int max) {
        StringBuilder expression = new StringBuilder();
        Random random = new Random();

        // 生成第一个数字
        int firstNumber = random.nextInt(max) + 1;
        expression.append(firstNumber);
        int currentSymbols = 0; // 当前运算符数量

        for (int i = 0; i < numSymbols; i++) {
            String symbol = SYMBOLS[random.nextInt(SYMBOLS.length)];
            int nextNumber = random.nextInt(max) + 1;

            // 确保不会导致负数
            if (symbol.equals("-") && firstNumber < nextNumber) {
                continue;
            }

            // 检查是否需要添加括号
            if (currentSymbols < 3 && (currentSymbols > 0 || random.nextBoolean())) { // 随机决定是否加括号
                StringBuilder subExpression = new StringBuilder();
                int subFirstNumber = random.nextInt(max) + 1;
                subExpression.append(subFirstNumber);
                int subSymbols = random.nextInt(2) + 1; // 子表达式至少有一个运算符
                int subOperatorsCount = 0; // 统计子表达式的运算符数量

                for (int j = 0; j < subSymbols; j++) {
                    String subSymbol = SYMBOLS[random.nextInt(SYMBOLS.length)];
                    int subNextNumber = random.nextInt(max) + 1;

                    // 确保不会导致负数
                    if (subSymbol.equals("-") && subFirstNumber < subNextNumber) {
                        continue;
                    }

                    subExpression.append(" ").append(subSymbol).append(" ").append(subNextNumber);
                    subFirstNumber = calculateIntermediateResult(subFirstNumber, subSymbol, subNextNumber);
                    subOperatorsCount++; // 增加子表达式运算符数量
                }

                // 仅在当前运算符加上子表达式运算符数量不超过3时才添加括号
                if (currentSymbols + subOperatorsCount < 4) {
                    expression.append(" ").append(symbol).append(" (").append(subExpression.toString()).append(")");
                    currentSymbols += subOperatorsCount + 1; // 更新当前运算符数量
                }
            } else {
                expression.append(" ").append(symbol).append(" ").append(nextNumber);
                currentSymbols++; // 增加当前运算符数量
            }

            // 更新当前数字
            firstNumber = calculateIntermediateResult(firstNumber, symbol, nextNumber);
        }

        this.infixExpression = expression.toString().trim();
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
            if (!isSymbol(token) && !token.isEmpty() && !token.equals("(") && !token.equals(")")) {
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
