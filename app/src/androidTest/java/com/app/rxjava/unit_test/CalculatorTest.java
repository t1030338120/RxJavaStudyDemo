package com.app.rxjava.unit_test;

import junit.framework.TestCase;

/**
 * 描述：
 * 作者：tyc
 */
public class CalculatorTest extends TestCase {

    private Calculator mCalculator;

    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }


    public void testSum() throws Exception {
        assertEquals(6d, mCalculator.sum(1d, 5d), 0);
    }

    public void testSubstract() throws Exception {

    }

    public void testDivide() throws Exception {

    }

    public void testMultiply() throws Exception {

    }
}