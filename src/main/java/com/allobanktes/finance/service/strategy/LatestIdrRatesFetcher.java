package com.allobanktes.finance.service.strategy;

import java.util.List;
import java.util.Map;

import com.allobanktes.finance.dto.LatestRatesResponse;
import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.exception.ExternalApiException;
import com.allobanktes.finance.service.client.FrankfurterClient;
import com.allobanktes.finance.service.spread.SpreadFactorCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LatestIdrRatesFetcher implements IDRDataFetcher {

    private final FrankfurterClient client;
    private final SpreadFactorCalculator calculator;
    private final String githubUsername;

    public LatestIdrRatesFetcher(
            FrankfurterClient client,
            SpreadFactorCalculator calculator,
            @Value("${app.github-username}") String githubUsername
    ) {
        this.client = client;
        this.calculator = calculator;
        this.githubUsername = githubUsername.toLowerCase();
    }

    @Override
    public String resourceType() {
        return "latest_idr_rates";
    }

    @Override
    public List<UnifiedResult> fetch() {
        LatestRatesResponse resp = client.latestIdrRates();

        if (resp.rates() == null || resp.rates().get("USD") == null) {
            throw new ExternalApiException("USD rate not found in latest rates response");
        }

        double rateUsd = resp.rates().get("USD");
        double spread = calculator.calculate(githubUsername);
        double usdBuySpreadIdr = (1.0 / rateUsd) * (1.0 + spread);

        Map<String, Object> payload = Map.of(
                "date", resp.date(),
                "base", resp.base(),
                "rate_usd", rateUsd,
                "spread_factor", spread,
                "USD_BuySpread_IDR", usdBuySpreadIdr,
                "rates", resp.rates()
        );

        return List.of(new UnifiedResult(resourceType(), payload));
    }
}