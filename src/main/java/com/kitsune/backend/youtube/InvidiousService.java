package com.kitsune.backend.youtube;

import com.kitsune.backend.error.RaceException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvidiousService {

    private final WebClient webClient;
    private final HolodexService holodexService;

    @Value("${config.invidious.endpoints}")
    private String[] endpoints;

    public <T> Mono<T> race(@NotNull Function<InvidiousInstance, Mono<T>> function) {
        var instances = Stream.of(endpoints)
                .map(endpoint -> new InvidiousInstance(webClient, endpoint))
                .map(function)
                .toList();

        return Mono.firstWithValue(instances)
                .onErrorMap(RaceException::recover);
    }

    public <T> Mono<T> race(@NotNull String path, @NotNull Map<String, ?> variables, @NotNull Class<T> type) {
        return race(instance -> instance.get(path, variables, type));
    }

    public Mono<InvidiousVideo> getVideo(@NotNull String videoId) {
        var variables = Map.of("videoId", videoId);
        return race("/api/v1/videos/{videoId}", variables, InvidiousVideo.class);
    }
}
