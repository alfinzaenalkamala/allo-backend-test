package com.allobanktes.finance.service.strategy;

import com.allobanktes.finance.dto.UnifiedResult;

import java.util.List;

public interface IDRDataFetcher {
    String resourceType();         // key map
    List<UnifiedResult> fetch();   // hasil final harus unified array
}
