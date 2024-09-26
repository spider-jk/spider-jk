package org.example;

public class fraction_work {
    private int numerator;  // 分子
    private int denominator = 1;  // 分母

    // 构造函数：从字符串解析
    public fraction_work(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }
        if (!s.contains("'") && !s.contains("/")) {
            numerator = Integer.parseInt(s);
        } else if (s.contains("'")) {
            String[] temp = s.split("['/]");
            int number = Integer.parseInt(temp[0]);
            numerator = Integer.parseInt(temp[1]);
            denominator = Integer.parseInt(temp[2]);
            this.numerator += number * denominator;
        } else {
            String[] temp = s.split("/");
            numerator = Integer.parseInt(temp[0]);
            denominator = Integer.parseInt(temp[1]);
        }
        if (denominator == 0) {
            throw new RuntimeException("分母不能为0");
        }
    }

    // 构造函数：从分子和分母创建分数
    public fraction_work(int numerator, int denominator) {
        if (denominator == 0) {
            throw new RuntimeException("分母不能为0");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }
    //生成真分数
    public static fraction_work fraction_random(int max){//随机生成小于max真分数或者自然数
        int p=(int)(Math.random()*100)+1;
        if(p<=15){//15%的概率生成整数，因为自然数太多了
            int fenzi=(int)(Math.random()*max);
            if(fenzi==0)
                fenzi=1;//防止0的生成
            return new fraction_work(fenzi,1);
        }
        else{
            int fenmu=(int)(Math.random()*max)+1;
            if(fenmu==max&&fenmu!=1)
                fenmu--;//防止分母超过范围
            int numerator=(int)(Math.random()*fenmu*max)+1;//分子范围在1到分母*max之间
            return new fraction_work(numerator,fenmu);
        }
    }
    // 判断分数是否为负数
    public boolean isNegative() {
        return (numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0);
    }

    // 求最大公因数（GCD）
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // 分数的运算方法
    public static fraction_work operation(String operator, fraction_work a, fraction_work m) {
        switch (operator) {
            case "+":
                return add(a, m);
            case "-":
                return sub(a, m);
            case "×":
                return mul(a, m);
            case "÷":
                return div(a, m);
            default:
                return null;
        }
    }

    // 加法
    private static fraction_work add(fraction_work a, fraction_work m) {
        int numerator = a.numerator * m.denominator + m.numerator * a.denominator;
        int denominator = a.denominator * m.denominator;
        return new fraction_work(numerator, denominator);
    }

    // 减法
    private static fraction_work sub(fraction_work a, fraction_work m) {
        int numerator = a.numerator * m.denominator - m.numerator * a.denominator;
        int denominator = a.denominator * m.denominator;
        return new fraction_work(numerator, denominator);
    }

    // 乘法
    private static fraction_work mul(fraction_work a, fraction_work m) {
        int numerator = a.numerator * m.numerator;
        int denominator = a.denominator * m.denominator;
        return new fraction_work(numerator, denominator);
    }

    // 除法
    private static fraction_work div(fraction_work a, fraction_work m) {
        if (m.numerator == 0) {
            return new fraction_work(-1,1);
        }
        int numerator = a.numerator * m.denominator;
        int denominator = a.denominator * m.numerator;
        return new fraction_work(numerator, denominator);
    }

    // 重写 toString() 方法
    @Override
    public String toString() {
        if (numerator == 0) return "0";
        if (denominator == 1) return String.valueOf(numerator);

        boolean isNegative = (numerator < 0) ^ (denominator < 0);
        numerator = Math.abs(numerator);
        denominator = Math.abs(denominator);
        int g = gcd(numerator, denominator);  // 使用最大公因数的方法化简
        numerator /= g;
        denominator /= g;
        //对于分数的格式输出处理，使得满足题目要求。
        if (numerator < denominator) {
            return (isNegative ? "-" : "") + numerator + "/" + denominator;
        } else {
            int wholePart = numerator / denominator;
            int remainder = numerator % denominator;
            if (remainder == 0) {
                return (isNegative ? "-" : "") + wholePart;
            } else {
                return (isNegative ? "-" : "") + wholePart + "'" + remainder + "/" + denominator;
            }
        }
    }
}
