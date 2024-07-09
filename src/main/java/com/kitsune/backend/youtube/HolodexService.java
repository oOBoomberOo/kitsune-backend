package com.kitsune.backend.youtube;

import com.kitsune.backend.error.UnknownHolodexVideoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolodexService {

    @Value("${config.holodex.api_key}")
    private String apiKey;

    @Value("${config.holodex.endpoint}")
    private String endpoint;

    private final WebClient webClient;


    public Mono<HolodexVideo> getVideo(String videoId) {
        var variables = Map.of("videoId", videoId);
        return webClient.get()
                .uri(endpoint + "/videos/{videoId}", variables)
                .header("x-apikey", apiKey)
                .retrieve()
                .onStatus(status -> status.isSameCodeAs(HttpStatus.NOT_FOUND), response -> Mono.just(new UnknownHolodexVideoException(videoId)))
                .bodyToMono(HolodexVideo.class)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorReturn(UnknownHolodexVideoException.class, HolodexVideo.builder().build());
    }

}
