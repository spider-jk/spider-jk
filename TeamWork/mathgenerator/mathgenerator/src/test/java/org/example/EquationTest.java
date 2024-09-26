package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class EquationTest {

    @Test
    public void testValidExpression() {
        Equation_p_c equation = new Equation_p_c("3 + 5 × 2");
        assertEquals("3 5 2 × +", equation.getPostfixExpression());
        assertNotNull(equation.getResult());
        assertFalse(equation.getResult().isNegative());
    }

    @Test
    public void testExpressionWithBrackets() {
        Equation_p_c equation = new Equation_p_c("(2 + 3) × 4");
        assertEquals("2 3 + 4 ×", equation.getPostfixExpression());
        assertNotNull(equation.getResult());
        assertFalse(equation.getResult().isNegative());
    }


    @Test
    public void testNegativeResultExpression() {
        Equation_p_c equation = new Equation_p_c("5 - 10");
        assertNull(equation.getResult()); // 结果应该为null，因为中间结果为负数
    }
}