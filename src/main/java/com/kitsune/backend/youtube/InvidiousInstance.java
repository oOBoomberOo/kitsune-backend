package com.kitsune.backend.youtube;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

public record InvidiousInstance(WebClient webClient, String endpoint) {
    public URI path(String path) {
        return path(path, Map.of());
    }

    public URI path(String path, Map<String, ?> variables) {
        return new DefaultUriBuilderFactory(endpoint)
                .expand(path, variables);
    }

    public <T> Mono<T> get(String path, Map<String, ?> variables, Class<T> type) {
        return webClient.get()
                .uri(path(path, variables))
                .retrieve()
                .bodyToMono(type)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(50)))
                .onErrorMap(Exceptions::isRetryExhausted, Throwable::getCause);
    }
}
