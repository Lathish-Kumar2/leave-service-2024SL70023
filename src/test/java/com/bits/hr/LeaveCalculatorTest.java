package com.bits.hr;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LeaveCalculatorTest {

    private LeaveCalculator calculator;

    @Before
    public void setUp() {
        calculator = new LeaveCalculator();
    }

   
    @Test
    public void testCalculateRemainingLeaveValid() {
        int remaining = calculator.calculateRemainingLeave(24, 10);
        assertEquals("Remaining balance should be 14", 14, remaining);
    }

   
    @Test
    public void testCalculateEncashment() {
        double encashment = calculator.calculateEncashment(10, 2500.0);
        assertEquals("Encashment value should be 25000.0", 25000.0, encashment, 0.001);
    }

   
    @Test(expected = IllegalArgumentException.class)
    public void testLeavesTakenExceedsTotalThrowsException() {
        calculator.calculateRemainingLeave(15, 20);
    }
}
