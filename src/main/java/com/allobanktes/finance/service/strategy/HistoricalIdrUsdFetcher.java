package com.allobanktes.finance.service.strategy;

import java.util.List;
import java.util.Map;

import com.allobanktes.finance.dto.HistoricalRatesResponse;
import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.exception.ExternalApiException;
import com.allobanktes.finance.service.client.FrankfurterClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class HistoricalIdrUsdFetcher implements IDRDataFetcher {

    private final FrankfurterClient client;

    public HistoricalIdrUsdFetcher(FrankfurterClient client) {
        this.client = client;
    }

    @Override
    public String resourceType() {
        return "historical_idr_usd";
    }

    @Override
    public List<UnifiedResult> fetch() {
        HistoricalRatesResponse resp = client.historicalIdrUsd();

        Map<String, Object> payload = Map.of(
                "start_date", resp.startDate(),
                "end_date", resp.endDate(),
                "base", resp.base(),
                "rates", resp.rates()
        );

        return List.of(new UnifiedResult(resourceType(), payload));
    }
}
