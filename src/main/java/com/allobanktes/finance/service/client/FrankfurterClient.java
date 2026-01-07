package com.allobanktes.finance.service.client;

import com.allobanktes.finance.dto.HistoricalRatesResponse;
import com.allobanktes.finance.dto.LatestRatesResponse;

import java.util.Map;

public interface FrankfurterClient {
    LatestRatesResponse latestIdrRates();
    HistoricalRatesResponse historicalIdrUsd();
    Map<String, String> currencies();
}
