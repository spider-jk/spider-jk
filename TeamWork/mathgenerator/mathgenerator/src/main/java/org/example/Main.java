package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static Set<String> resultSet = new HashSet<>();//用来筛选是否重复
    private static final String exercisesFilePath = "Exercises.txt";//题目文件
    private static final String answersFilePath = "Answers.txt";//答案文件

    public static void main(String[] args) {//筛选参数个数是否有误
        int length = args.length;
        if (length != 4 && length != 2) {
            throw new RuntimeException("输入的参数个数有误");
        }
        try {
            run(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void run(String[] args) throws IOException {
        int num = 0;
        int max = 0;
        String exerciseFile = null;
        String answerFile = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e")) {
                if (i + 1 < args.length) {
                    exerciseFile = args[i + 1];
                }
            }
            if (args[i].equals("-a")) {
                if (i + 1 < args.length) {
                    answerFile = args[i + 1];
                }
            }
            if (args[i].equals("-n")) {
                if (i + 1 < args.length) {
                    num = Integer.parseInt(args[i + 1]);
                    if (num <= 0) throw new RuntimeException("请求的表达式数量过少");
                }
            }
            if (args[i].equals("-r")) {
                if (i + 1 < args.length) {
                    max = Integer.parseInt(args[i + 1]);
                    if (max <= 0) throw new RuntimeException("操作数范围设置小于零");
                }
            }

        }

        if (num != 0 && max == 0) max = 10; // 只要求生成题目时，最大范围为10
        if (num == 0 && max != 0) num = 10; // 只要求题目范围时，则生成10道题目
        if (num != 0 && max != 0) generate(num, max);//生成
        if (exerciseFile != null && answerFile != null) judge(exerciseFile, answerFile);//判断
        if (num == 0 && max == 0 && exerciseFile == null && answerFile == null)
            throw new RuntimeException("输入的参数格式不符合题目要求");
    }

    private static void generate(int num, int max) throws IOException {
        int i = 0;
        file_work.ClearFile(exercisesFilePath);//先清空
        file_work.ClearFile(answersFilePath);
        while (i < num) {
            int numSymbol = (int) (Math.random() * 3 + 1); // 随机生成1-3个运算符
            Equation_p_c equation = new Equation_p_c(numSymbol, max); // 生成表达式
            // 检查表达式是否有效
            if (!isValidEquation(equation)) continue;//即结果不为0
            String result = equation.getResult().toString();
            if (!resultSet.isEmpty() && resultSet.contains(result))//判断等式是否有重复性，如果则有重新生成
                continue;
            resultSet.add(result); // 记录结果，防止重复
            String question = (i + 1) + ". " + equation.getInfixExpression() + "=";
            result = (i + 1) + ". " + result;
            file_work.WriteFile(exercisesFilePath, question); // 将题目写入文件
            file_work.WriteFile(answersFilePath, result); // 将答案写入文件
            i++;
        }
        System.out.println("生成"+num+"道操作数范围在"+max+"内的四则运算式完毕，已存入目标文件"+exercisesFilePath+"中。");
    }

    private static boolean isValidEquation(Equation_p_c equation) {
        // 检查结果是否相等于表达式
        if (equation.getResult().toString().equals("0")) return false; // 不允许结果为0
        return true;
    }

    private static void judge(String exercises, String answers) throws IOException {//判断对错
        List<String> exerciseList = new ArrayList<>();
        List<String> answerList = new ArrayList<>();
        file_work.ReadFile(exercises, exerciseList); // 读取题目
        file_work.ReadFile(answers, answerList); // 读取答案
        if (exerciseList.size() != answerList.size()) {
            throw new RuntimeException("题目数量与答案数量不一致");
        }
        boolean[] tag = new boolean[exerciseList.size()]; // 标记答案是否正确
        int num = 0; // 标记正确答案个数
        for (int i = 0; i < exerciseList.size(); i++) {
            Equation_p_c equation = new Equation_p_c(exerciseList.get(i));
            if (equation.getResult().toString().equals(answerList.get(i))) {
                tag[i] = true;
                num++; // 记录正确的答案个数
            }
        }
        StringBuilder correct = new StringBuilder("Correct:" + num + "(");
        StringBuilder wrong = new StringBuilder("Wrong:" + (exerciseList.size() - num) + "(");
        for (int i = 0; i < tag.length; i++) {
            if (tag[i])
                correct.append(i + 1).append(",");
            else
                wrong.append(i + 1).append(",");
        }
        if (correct.charAt(correct.length() - 1) != '(')//检查最后一个字符是不是左括号，如果不是左括号就是逗号，那就要舍去逗号再加
            correct = new StringBuilder(correct.substring(0, correct.length() - 1) + ")");
        else
            correct.append(")"); // 补全括号
        if (wrong.charAt(wrong.length() - 1) != '(')//与上同理
            wrong = new StringBuilder(wrong.substring(0, wrong.length() - 1) + ")");
        else
            wrong.append(")"); // 补全括号
        file_work.ClearFile("Grade.txt");//先清理一下里面的内容
        file_work.WriteFile("Grade.txt", correct.toString());
        file_work.WriteFile("Grade.txt", wrong.toString());
        System.out.println("判断完毕，结果已经存入目标文件Grade.txt中。");
    }
}
