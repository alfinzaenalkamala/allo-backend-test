package com.allobanktes.finance.service.spread;

import org.springframework.stereotype.Component;

@Component
public class SpreadFactorCalculator {

    public double calculate(String githubUsernameLowercase) {
        int sum = 0;
        for (char c : githubUsernameLowercase.toCharArray()) {
            sum += (int) c;
        }
        return (sum % 1000) / 100000.0;
    }
}
