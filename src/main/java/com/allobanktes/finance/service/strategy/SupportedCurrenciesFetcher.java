package com.allobanktes.finance.service.strategy;

import java.util.List;
import java.util.Map;

import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.exception.ExternalApiException;
import com.allobanktes.finance.service.client.FrankfurterClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class SupportedCurrenciesFetcher implements IDRDataFetcher {

    private final FrankfurterClient client;

    public SupportedCurrenciesFetcher(FrankfurterClient client) {
        this.client = client;
    }

    @Override
    public String resourceType() {
        return "supported_currencies";
    }

    @Override
    public List<UnifiedResult> fetch() {
        Map<String, String> currencies = client.currencies();
        return List.of(new UnifiedResult(resourceType(), Map.of("currencies", currencies)));
    }
}

