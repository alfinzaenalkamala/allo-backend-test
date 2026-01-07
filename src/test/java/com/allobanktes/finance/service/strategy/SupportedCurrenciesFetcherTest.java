package com.allobanktes.finance.service.strategy;

import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.service.client.FrankfurterClient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupportedCurrenciesFetcherTest {

    @Test
    void fetch_shouldReturnUnifiedResult_withCurrencies() {
        FrankfurterClient client = mock(FrankfurterClient.class);

        when(client.currencies()).thenReturn(Map.of(
                "USD", "United States Dollar",
                "IDR", "Indonesian Rupiah"
        ));

        SupportedCurrenciesFetcher fetcher = new SupportedCurrenciesFetcher(client);

        List<UnifiedResult> results = fetcher.fetch();

        assertEquals(1, results.size());
        UnifiedResult r = results.get(0);
        assertEquals("supported_currencies", r.resourceType());

        Object currencies = r.data().get("currencies");
        assertNotNull(currencies);

        verify(client, times(1)).currencies();
    }
}
