package com.allobanktes.finance.dto;

import java.util.Map;

public record UnifiedResult(
        String resourceType,
        Map<String, Object> data
) {}
