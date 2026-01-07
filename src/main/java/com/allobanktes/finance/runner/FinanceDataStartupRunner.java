package com.allobanktes.finance.runner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.service.FinanceDataStore;
import com.allobanktes.finance.service.strategy.IDRDataFetcher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FinanceDataStartupRunner implements ApplicationRunner {

    private final List<IDRDataFetcher> fetchers;
    private final FinanceDataStore store;

    public FinanceDataStartupRunner(List<IDRDataFetcher> fetchers, FinanceDataStore store) {
        this.fetchers = fetchers;
        this.store = store;
    }

    @Override
    public void run(ApplicationArguments args) {
        Map<String, List<UnifiedResult>> snapshot =
                fetchers.stream()
                        .collect(Collectors.toUnmodifiableMap(
                                IDRDataFetcher::resourceType,
                                IDRDataFetcher::fetch
                        ));

        store.setSnapshot(snapshot);
    }
}
