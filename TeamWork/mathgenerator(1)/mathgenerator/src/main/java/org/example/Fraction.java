package org.example;
public class Fraction {
    private int numerator; // 分子
    private int denominator = 1; // 分母

    public Fraction(String s) {
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

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new RuntimeException("分母不能为0");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    private static int gcd(int a, int b) { // 求最大公因数
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static Fraction operation(String operator, Fraction fa, Fraction fb) {
        if (operator.equals("+")) {
            return add(fa, fb);
        } else if (operator.equals("-")) {
            return sub(fa, fb);
        } else if (operator.equals("×")) {
            return mul(fa, fb);
        } else if (operator.equals("÷")) {
            return div(fa, fb);
        }
        return null;
    }

    private static Fraction add(Fraction fa, Fraction fb) {
        int numerator = fa.numerator * fb.denominator + fb.numerator * fa.denominator;
        int denominator = fa.denominator * fb.denominator;
        return new Fraction(numerator, denominator);
    }

    private static Fraction sub(Fraction fa, Fraction fb) {
        int numerator = fa.numerator * fb.denominator - fb.numerator * fa.denominator;
        int denominator = fa.denominator * fb.denominator;
        return new Fraction(numerator, denominator);
    }

    private static Fraction mul(Fraction fa, Fraction fb) {
        int numerator = fa.numerator * fb.numerator;
        int denominator = fa.denominator * fb.denominator;
        return new Fraction(numerator, denominator);
    }

    private static Fraction div(Fraction fa, Fraction fb) {
        if (fb.numerator == 0) {
            throw new RuntimeException("除数不能为0");
        }
        int numerator = fa.numerator * fb.denominator;
        int denominator = fa.denominator * fb.numerator;
        return new Fraction(numerator, denominator);
    }

    @Override
    public String toString() {
        if (numerator == 0) return "0";
        if (denominator == 1) return String.valueOf(numerator);
        boolean isNegative = (numerator < 0) ^ (denominator < 0);
        numerator = Math.abs(numerator);
        denominator = Math.abs(denominator);
        int g = gcd(numerator, denominator);
        numerator /= g;
        denominator /= g;

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
