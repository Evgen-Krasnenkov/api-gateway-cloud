package com.buzz.cloud.app;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class CustomFilter implements GlobalFilter {
    Logger logger = LoggerFactory.getLogger(CustomFilter.class);
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, @NotNull GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Authorization = {}", request.getHeaders().getFirst(AUTHORIZATION));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpStatus statusCode = exchange.getResponse().getStatusCode();
            logger.info("Response status code = {}", statusCode);
        }));
    }
}
