package com.allobanktes.finance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.frankfurter")
public record FrankfurterProperties(String baseUrl, int timeoutMs) {}