package com.kitsune.backend.youtube;

import com.kitsune.backend.api.video.VideoInfo;
import com.kitsune.backend.error.RaceException;
import com.kitsune.backend.model.VideoType;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvidiousService {

    private final WebClient webClient;

    @Value("${config.invidious.endpoints}")
    private String[] endpoints;

    public <T> Mono<T> race(@NotNull Function<InvidiousInstance, Mono<T>> function) {
        var instances = Stream.of(endpoints)
                .map(endpoint -> new InvidiousInstance(webClient, endpoint))
                .map(function)
                .toList();

        return Mono.firstWithValue(instances)
                .onErrorMap(err -> RaceException.from(Exceptions.unwrapMultiple(err.getCause())));
    }

    public <T> Mono<T> race(@NotNull String path, @NotNull Map<String, ?> variables, @NotNull Class<T> type) {
        return race(instance -> instance.get(path, variables, type));
    }

    public Mono<VideoData> getVideo(String videoId) {
        var variables = Map.of("videoId", videoId);

        return race("/api/v1/videos/{videoId}", variables, VideoData.class)
                .doOnNext(videoData -> log.info("Fetched {}: {}", videoId, videoData));
    }

    public Mono<VideoInfo> getVideoInfo(@NotNull String videoId) {
        var variables = Map.of("videoId", videoId);

        return race("/api/v1/videos/{videoId}", variables, VideoData.class)
                .map(this::mapVideoInfo);
    }

    private VideoInfo mapVideoInfo(VideoData video) {
        VideoType videoType = VideoType.UPLOAD;

        if (video.isUpcoming()) {
            videoType = VideoType.UPCOMING;
        }

        if (video.isLiveNow() || video.isPostLiveDvr()) {
            videoType = VideoType.LIVE;
        }

        if (video.isPremiere()) {
            videoType = VideoType.PREMIERE;
        }

        if (video.isPaid()) {
            videoType = VideoType.PRIVATE;
        }

        return VideoInfo.builder()
                .type(videoType)
                .title(video.getTitle())
                .author(video.getAuthor())
                .description(video.getDescription())
                .uploadDate(video.getPublished())
                .publishDate(video.getPublishedDate())
                .views(video.getViewCount())
                .build();
    }
}
