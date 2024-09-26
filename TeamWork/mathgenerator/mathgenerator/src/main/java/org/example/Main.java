package org.example;

import java.io.*;
import java.util.*;

public class Main {
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
        int number = 0;
        int max = 0;
        String exercises_file = null;
        String answer_file = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e")) {
                if (i + 1 < args.length) {
                    exercises_file = args[i + 1];
                }
            }
            if (args[i].equals("-a")) {
                if (i + 1 < args.length) {
                    answer_file = args[i + 1];
                }
            }
            if (args[i].equals("-n")) {
                if (i + 1 < args.length) {
                    number = Integer.parseInt(args[i + 1]);
                    if (number <= 0) throw new RuntimeException("请求的表达式数量过少");
                }
            }
            if (args[i].equals("-r")) {
                if (i + 1 < args.length) {
                    max = Integer.parseInt(args[i + 1]);
                    if (max <= 0) throw new RuntimeException("操作数范围设置小于零");
                }
            }
        }
        if (number != 0 && max == 0) max = 10; // 只要求生成题目时，最大范围为10
        if (number == 0 && max != 0) number = 10; // 只要求题目范围时，则生成10道题目
        if (number != 0 && max != 0) generate(number, max);//生成表达式
        if (exercises_file != null && answer_file != null) judge(exercises_file, answer_file);//判断答案
        if (number == 0 && max == 0 && exercises_file == null && answer_file == null)
            throw new RuntimeException("输入的参数格式不符合题目要求");
    }

    public static void generate(int num, int max) throws IOException {
        int i = 0;
        String[]result2=new String[num];
        for (int m =0;m<num;m++){
            result2[m]="-1";

        }
        file_work.ClearFile(exercisesFilePath);//先清空
        file_work.ClearFile(answersFilePath);
        System.out.println("生成中...请稍后");
        while (i < num) {
            int numSymbol = (int) (Math.random() * 3 + 1); // 随机生成1-3个运算符
            Equation_p_c equation = new Equation_p_c(numSymbol, max); // 生成表达式
            // 检查表达式是否有效
            if (!isValidEquation(equation)) continue;//即结果不为0
            String result = equation.getResult().toString();
            if (result2[0]!="-1"&&!isPass(equation,result2,result)) {//判断结果有无重复，如果有重复就把重复的式子进行操作数比对，如果都相同才重新生成
                System.out.println("重新生成"); continue;}
            result2[i]=result;
            String question = (i + 1) + ". " + equation.getInfixExpression() + "=";
            result = (i + 1) + ". " + result;
            file_work.WriteFile(exercisesFilePath, question); // 将题目写入文件
            file_work.WriteFile(answersFilePath, result); // 将答案写入文件
            i++;
        }
        System.out.println("生成"+num+"道操作数范围在"+max+"内的四则运算式完毕，已存入目标文件"+exercisesFilePath+"中。");
    }

    public static boolean isValidEquation(Equation_p_c equation) {
        // 检查结果是否相等于表达式
        if (equation.getResult().toString().equals("0")) return false; // 不允许结果为0
        return true;
    }
    private static boolean isPass(Equation_p_c equation,String[]results2,String result){//找重复结果的式子角标
        int pointer=0;//用于寻找重复的指针
        int pointer2=0;//repeat重复数目的指针
        int[]repeat=new int[results2.length];
        for (int j=0;j<repeat.length;j++){
            repeat[j]=0;
        }
        while(results2[pointer]!="-1"){//如果指向的已经到题目上限了就跳出循环
            if (results2[pointer].equals(result)){
                repeat[pointer2]=pointer+1;
                pointer2++;
            }
            pointer++;
        }
        if (pointer2==0) return true;//如果没有重复，就直接过
        if(pointer2!=0){
            for (int j=0;j<pointer2;j++){//对重复的式子进行下一步操作数比对
                if (isSame(equation,repeat[j])) return false;
            }
        }
        return true;
    }
    private static boolean isSame(Equation_p_c equantion,int pointer2){//对重复的式子进行操作数比对
        File file=new File(exercisesFilePath);
        String repeat_line;//用来存放之前结果重复的式子
        try {
            LineNumberReader lnr=new LineNumberReader(new FileReader(file));
            while((repeat_line = lnr.readLine()) != null){
                if(lnr.getLineNumber()==pointer2){//根据角标读取式子
                    break;
                }
            }
            int index = repeat_line.indexOf(".");
            repeat_line = repeat_line.substring(index + 1, repeat_line.length() - 1);//将题号删除
            String[]repeat_equantion=repeat_line.split("(?=[+\\-×÷()])|(?<=[+\\-×÷()])");//下面这几句是用来筛选出两个式子中的操作数并装入字符串数组中
            String[]equantion_now=equantion.toString().split("(?=[+\\-×÷()])|(?<=[+\\-×÷()])");
            repeat_equantion= Arrays.stream(repeat_equantion)
                    .filter(part -> !part.matches("[+\\-×÷()\\s]*"))  // 过滤掉运算符、空格等无效部分
                    .toArray(String[]::new);
            equantion_now=Arrays.stream(equantion_now)
                    .filter(part -> !part.matches("[+\\-×÷()\\s]*"))  // 过滤掉运算符、空格等无效部分
                    .toArray(String[]::new);
            if (repeat_equantion.length!=equantion_now.length) return false;
            for (int q=0;q<equantion_now.length;q++){
                int biaozhi=0;
                for (int p=0;p<repeat_equantion.length;p++){
                    if (!equantion_now[q].equals(repeat_equantion[p])) biaozhi++;
                }
                if(biaozhi==equantion_now.length) return false;
            }
            lnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("判定重复时读取文件失败");
        } catch (IOException e) {
            System.out.println("判定重复时读取文件的行数失败");
        }
        return true;
    }
    public static void judge(String exercises, String answers) throws IOException {//判断对错
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
