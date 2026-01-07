package com.allobanktes.finance.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.allobanktes.finance.dto.UnifiedResult;
import org.springframework.stereotype.Component;

@Component
public class FinanceDataStore {

    // AtomicReference supaya publish snapshot aman untuk multi-thread
    private final AtomicReference<Map<String, List<UnifiedResult>>> snapshotRef = new AtomicReference<>();

    public void setSnapshot(Map<String, List<UnifiedResult>> snapshot) {
        // set sekali; kalau mau strict, bisa throw jika sudah pernah set
        snapshotRef.set(Map.copyOf(snapshot));
    }

    public List<UnifiedResult> getByResourceType(String resourceType) {
        Map<String, List<UnifiedResult>> snap = snapshotRef.get();
        if (snap == null) return List.of();
        return snap.getOrDefault(resourceType, List.of());
    }

    public boolean isReady() {
        return snapshotRef.get() != null;
    }
}
