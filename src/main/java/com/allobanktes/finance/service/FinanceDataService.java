package com.allobanktes.finance.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.service.strategy.IDRDataFetcher;
import org.springframework.stereotype.Service;

@Service
public class FinanceDataService {

    private final Map<String, IDRDataFetcher> strategyMap;
    private final FinanceDataStore store;

    public FinanceDataService(List<IDRDataFetcher> fetchers, FinanceDataStore store) {
        this.strategyMap = fetchers.stream()
                .collect(Collectors.toUnmodifiableMap(IDRDataFetcher::resourceType, Function.identity()));
        this.store = store;
    }

    public List<UnifiedResult> getData(String resourceType) {
        // validasi resourceType exists (tanpa if di controller)
        if (!strategyMap.containsKey(resourceType)) {
            throw new IllegalArgumentException("Unsupported resourceType: " + resourceType);
        }
        // data selalu dari in-memory store
        return store.getByResourceType(resourceType);
    }
}
