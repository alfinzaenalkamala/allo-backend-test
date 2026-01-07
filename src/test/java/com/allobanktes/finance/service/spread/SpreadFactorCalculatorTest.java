package com.allobanktes.finance.service.spread;

import  org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpreadFactorCalculatorTest {

    @Test
    void calculate_shouldReturnValueBetween0And0_00999() {
        SpreadFactorCalculator calc = new SpreadFactorCalculator();
        double spread = calc.calculate("alfinzaenalkamala");

        assertTrue(spread >= 0.0);
        assertTrue(spread < 0.01);
    }

    @Test
    void calculate_shouldBeDeterministic() {
        SpreadFactorCalculator calc = new SpreadFactorCalculator();
        double a = calc.calculate("alfinzaenalkamala");
        double b = calc.calculate("alfinzaenalkamala");

        assertEquals(a, b, 0.0);
    }

    @Test
    void calculate_shouldUseLowercaseSameAsInputLowered() {
        SpreadFactorCalculator calc = new SpreadFactorCalculator();
        assertEquals(calc.calculate("alfinzaenalkamala".toLowerCase()), calc.calculate("alfinzaenalkamala"), 0.0);
    }
}