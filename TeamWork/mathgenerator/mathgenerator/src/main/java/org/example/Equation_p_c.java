package org.example;


import java.util.Random;
import java.util.Stack;

public class Equation_p_c {
    private static final String[] SYMBOLS = {"+", "-", "×", "÷"};
    private String infixExpression;
    private String postfixExpression;
    private fraction_work result;

    // 构造函数：从已有表达式解析
    public Equation_p_c(String expression) {
        this.infixExpression = expression.replaceAll(" ", "");
        this.postfixExpression = infixToPostfix(this.infixExpression);
        this.result = calculatePostfix(this.postfixExpression);
    }

    // 构造函数：随机生成表达式，避免负数中间结果，保留括号
    public Equation_p_c(int numSymbols, int max) {
        do {
            // 不断生成直到每一步计算中没有负数结果
            this.infixExpression = generateExpression(numSymbols, max);
            this.postfixExpression = infixToPostfix(this.infixExpression);
            this.result = calculatePostfix(this.postfixExpression);
        } while (this.result == null);  // 如果中间结果出现负数或计算异常，重新生成
    }

    // 生成随机表达式（含括号）
    private String generateExpression(int numSymbols, int max) {
        StringBuilder expression = new StringBuilder();
        Random random = new Random();
        int numOpenBrackets = 0; // 已插入的左括号数量
        int numCloseBrackets = 0; // 已插入的右括号数量
        boolean insideBrackets = false; // 标记当前是否在括号内
        int biaozhi=0;
        int distance=0;
        // 生成符号和数字，并随机插入括号
        for (int i = 0; i < numSymbols; i++) {
            String symbol = SYMBOLS[random.nextInt(SYMBOLS.length)];
            fraction_work nextNumber= fraction_work.fraction_random(max);

            if(i==0){
                // 生成第一个数字
                if (!insideBrackets && random.nextBoolean() && numOpenBrackets < numSymbols / 2) {
                    expression.append(" (");
                    numOpenBrackets++;
                    insideBrackets = true; // 标记进入括号内
                    distance+=1;
                }
                fraction_work firstNumber = fraction_work.fraction_random(max);
                expression.append(firstNumber);
            }

            // 添加符号
            expression.append(" ").append(symbol);
            //随机决定是否插入左括号，但不能超过符号数一半且在最后一个操作数前不能生成左括号
            if (!insideBrackets && random.nextBoolean() && numOpenBrackets < numSymbols / 2&&i+1<numSymbols) {
                expression.append(" (");
                numOpenBrackets++;
                insideBrackets = true; // 标记进入括号内
            }
            //添加操作数
            expression.append(" ").append(nextNumber);
            if (distance!=0) distance+=1;
            // 随机决定是否插入右括号，但必须成对关闭括号且最后一个操作数后应该由最后面的循环判定，这里直接不能生成
            if (insideBrackets && random.nextBoolean() && numCloseBrackets < numOpenBrackets&&distance>=2&&i+1<numSymbols) {
                expression.append(")");
                distance=0;
                numCloseBrackets++;
                insideBrackets = false; // 关闭括号
            }
        }

        // 如果有未关闭的括号，通过判定是否左括号在首位来决定是否闭合
        while (numCloseBrackets < numOpenBrackets) {
            String test=expression.toString().trim();
            //首位为左括号，直接删除左括号，否则闭合
            if (test.charAt(0)=='('&&numCloseBrackets+1==numOpenBrackets) {
                biaozhi=1;
            }else {
                expression.append(")");
            }
            numCloseBrackets++;
        }
        if (biaozhi==1) {
            String test=expression.toString().trim().substring(1);
            return test;
        }
        return expression.toString().trim();
    }

    // 中缀表达式转后缀表达式，处理运算符优先级
    private String infixToPostfix(String infixExpression) {
        StringBuilder result = new StringBuilder();// 用于存储最终输出的后缀表达式
        Stack<String> stack = new Stack<>();// 用于存储运算符的栈
        // 使用正则表达式将输入的中缀表达式按符号和数字分割成数组
        String[] tokens = infixExpression.split("(?=[-+×÷()])|(?<=[-+×÷()])");
        for (String token : tokens) {
            // 如果当前标记是操作数（不是符号、括号且不为空），直接添加到结果中
            if (!isSymbol(token) && !token.equals("(") && !token.equals(")") && !token.isEmpty()) {
                result.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                // 弹出栈中的符号直到遇到左括号为止，将弹出的符号添加到结果中
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty()) {
                    stack.pop(); // 移除左括号
                }
                // 如果当前标记是运算符
            } else if (isSymbol(token)) {
                // 当栈不为空且栈顶符号的优先级高于或等于当前符号时，弹出栈顶符号并加入结果
                while (!stack.isEmpty() && symbolPriority(stack.peek()) >= symbolPriority(token)) {
                    result.append(stack.pop()).append(" ");
                }
                // 将当前运算符压入栈中
                stack.push(token);
            }
        }
    // 如果栈中还有剩余的符号，依次弹出并加入结果
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }
        // 返回最终的后缀表达式，去掉多余的空格
        return result.toString().trim();
    }

    // 计算后缀表达式，确保中间结果不为负数
    private fraction_work calculatePostfix(String expression) {
        String[] tokens = expression.split(" ");
        Stack<fraction_work> stack = new Stack<>();

        for (String token : tokens) {
            if (!isSymbol(token) && !token.isEmpty()) {
                stack.push(new fraction_work(token));  // 操作数入栈
            } else if (isSymbol(token)) {
                if (stack.size() < 2) {
                    return null;  // 表达式错误，重新生成
                }
                fraction_work b = stack.pop();
                fraction_work a = stack.pop();
                fraction_work result = fraction_work.operation(token, a, b);
                // 如果中间结果为负数，停止并重新生成表达式
                if (result.isNegative()) {
                    return null;
                }
                stack.push(result);  // 继续计算
            }
        }

        return stack.isEmpty() ? null : stack.pop();  // 最终结果
    }

    // 判断字符是否是运算符
    private boolean isSymbol(String s) {
        for (String symbol : SYMBOLS) {
            if (s.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    // 获取运算符的优先级
    private int symbolPriority(String s) {
        if (s.equals("+") || s.equals("-")) {
            return 1;
        } else if (s.equals("×") || s.equals("÷")) {
            return 2;
        }
        return 0;
    }

    // 获取中缀表达式
    public String getInfixExpression() {
        return infixExpression;
    }

    // 获取后缀表达式
    public String getPostfixExpression() {
        return postfixExpression;
    }

    // 获取计算结果
    public fraction_work getResult() {
        return result;
    }
}
