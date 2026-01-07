package com.allobanktes.finance.service.strategy;
import com.allobanktes.finance.dto.HistoricalRatesResponse;
import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.service.client.FrankfurterClient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoricalIdrUsdFetcherTest {

    @Test
    void fetch_shouldReturnUnifiedResult_withHistoricalRates() {
        FrankfurterClient client = mock(FrankfurterClient.class);

        HistoricalRatesResponse apiResp = new HistoricalRatesResponse(
                "IDR",
                "2024-01-01",
                "2024-01-05",
                Map.of(
                        "2024-01-01", Map.of("USD", 0.000065),
                        "2024-01-02", Map.of("USD", 0.000064)
                )
        );

        when(client.historicalIdrUsd()).thenReturn(apiResp);

        HistoricalIdrUsdFetcher fetcher = new HistoricalIdrUsdFetcher(client);

        List<UnifiedResult> results = fetcher.fetch();

        assertEquals(1, results.size());
        UnifiedResult r = results.get(0);

        assertEquals("historical_idr_usd", r.resourceType());
        assertEquals("IDR", r.data().get("base"));
        assertEquals("2024-01-01", r.data().get("start_date"));
        assertEquals("2024-01-05", r.data().get("end_date"));

        verify(client, times(1)).historicalIdrUsd();
    }
}