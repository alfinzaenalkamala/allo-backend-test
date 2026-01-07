package com.allobanktes.finance.config;

import java.time.Duration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component("frankfurterWebClient")
public class FrankfurterWebClientFactoryBean implements FactoryBean<WebClient> {

    private final FrankfurterProperties props;

    public FrankfurterWebClientFactoryBean(FrankfurterProperties props) {
        this.props = props;
    }

    @Override
    public WebClient getObject() {
        return WebClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return WebClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}