package com.allobanktes.finance.service.strategy;

import com.allobanktes.finance.dto.LatestRatesResponse;
import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.exception.ExternalApiException;
import com.allobanktes.finance.service.client.FrankfurterClient;
import com.allobanktes.finance.service.spread.SpreadFactorCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LatestIdrRatesFetcherTest {

    private FrankfurterClient client;
    private SpreadFactorCalculator calculator;

    @BeforeEach
    void setup() {
        client = mock(FrankfurterClient.class);
        calculator = new SpreadFactorCalculator();
    }

    @Test
    void fetch_shouldReturnUnifiedResult_withUsdBuySpreadIdr() {
        String username = "alfinzaenalkamala";
        double rateUsd = 0.000064; // contoh
        LatestRatesResponse apiResp = new LatestRatesResponse(
                1.0, "IDR", "2026-01-06", Map.of("USD", rateUsd, "EUR", 0.000059)
        );

        when(client.latestIdrRates()).thenReturn(apiResp);

        LatestIdrRatesFetcher fetcher = new LatestIdrRatesFetcher(client, calculator, username);

        List<UnifiedResult> results = fetcher.fetch();

        assertEquals(1, results.size());
        UnifiedResult r = results.get(0);
        assertEquals("latest_idr_rates", r.resourceType());

        Map<String, Object> data = r.data();
        assertEquals("IDR", data.get("base"));
        assertEquals(rateUsd, (double) data.get("rate_usd"), 0.0);

        double spread = (double) data.get("spread_factor");
        double expectedSpread = calculator.calculate(username.toLowerCase());
        assertEquals(expectedSpread, spread, 0.0);

        double expected = (1.0 / rateUsd) * (1.0 + expectedSpread);
        double actual = (double) data.get("USD_BuySpread_IDR");

        assertEquals(expected, actual, 1e-9);

        verify(client, times(1)).latestIdrRates();
    }

    @Test
    void fetch_shouldThrow_whenUsdRateMissing() {
        String username = "alfinzaenalkamala";
        LatestRatesResponse apiResp = new LatestRatesResponse(
                1.0, "IDR", "2026-01-06", Map.of("EUR", 0.000059)
        );

        when(client.latestIdrRates()).thenReturn(apiResp);

        LatestIdrRatesFetcher fetcher = new LatestIdrRatesFetcher(client, calculator, username);

        assertThrows(ExternalApiException.class, fetcher::fetch);
    }
}