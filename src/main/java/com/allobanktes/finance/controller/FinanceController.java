package com.allobanktes.finance.controller;

import java.util.List;

import com.allobanktes.finance.dto.UnifiedResult;
import com.allobanktes.finance.service.FinanceDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance/data")
public class FinanceController {

    private final FinanceDataService service;

    public FinanceController(FinanceDataService service) {
        this.service = service;
    }

    @GetMapping("/{resourceType}")
    public List<UnifiedResult> get(@PathVariable String resourceType) {
        return service.getData(resourceType);
    }
}