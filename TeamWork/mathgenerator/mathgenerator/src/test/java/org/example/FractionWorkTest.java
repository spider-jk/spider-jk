package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class FractionWorkTest {

    @Test
    public void testFractionCreation() {
        fraction_work frac = new fraction_work("1/2");
        assertEquals("1/2", frac.toString());

        frac = new fraction_work("2'3/4");
        assertEquals("2'3/4", frac.toString());
    }

    @Test
    public void testFractionRandom() {
        fraction_work randomFrac = fraction_work.fraction_random(10);
        assertTrue(randomFrac.toString().matches("-?\\d+'?\\d*/\\d+|\\d+")); // 检查随机分数格式
    }

    @Test
    public void testFractionOperations() {
        fraction_work a = new fraction_work("1/2");
        fraction_work b = new fraction_work("1/4");

        fraction_work sum = fraction_work.operation("+", a, b);
        assertEquals("3/4", sum.toString());

        fraction_work difference = fraction_work.operation("-", a, b);
        assertEquals("1/4", difference.toString());

        fraction_work product = fraction_work.operation("×", a, b);
        assertEquals("1/8", product.toString());

        fraction_work quotient = fraction_work.operation("÷", a, b);
        assertEquals("2", quotient.toString());
    }
}
