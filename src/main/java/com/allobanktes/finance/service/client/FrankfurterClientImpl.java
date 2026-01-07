package com.allobanktes.finance.service.client;

import com.allobanktes.finance.dto.HistoricalRatesResponse;
import com.allobanktes.finance.dto.LatestRatesResponse;
import com.allobanktes.finance.exception.ExternalApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class FrankfurterClientImpl implements FrankfurterClient {

    private final WebClient client;

    public FrankfurterClientImpl(WebClient frankfurterWebClient) {
        this.client = frankfurterWebClient;
    }

    @Override
    public LatestRatesResponse latestIdrRates() {
        LatestRatesResponse resp = client.get()
                .uri("/latest?base=IDR")
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        r -> r.bodyToMono(String.class).map(body -> new ExternalApiException("Frankfurter error: " + body)))
                .bodyToMono(LatestRatesResponse.class)
                .block();

        if (resp == null) throw new ExternalApiException("Latest rates response is null");
        return resp;
    }

    @Override
    public HistoricalRatesResponse historicalIdrUsd() {
        HistoricalRatesResponse resp = client.get()
                .uri("/2024-01-01..2024-01-05?from=IDR&to=USD")
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        r -> r.bodyToMono(String.class).map(body -> new ExternalApiException("Frankfurter error: " + body)))
                .bodyToMono(HistoricalRatesResponse.class)
                .block();

        if (resp == null) throw new ExternalApiException("Historical response is null");
        return resp;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> currencies() {
        Map<String, String> resp = client.get()
                .uri("/currencies")
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        r -> r.bodyToMono(String.class).map(body -> new ExternalApiException("Frankfurter error: " + body)))
                .bodyToMono(Map.class)
                .block();

        if (resp == null) throw new ExternalApiException("Currencies response is null");
        return resp;
    }
}
