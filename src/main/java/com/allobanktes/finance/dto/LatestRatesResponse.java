package com.allobanktes.finance.dto;

import java.util.Map;

public record LatestRatesResponse(
        double amount,
        String base,
        String date,
        Map<String, Double> rates
) {}
