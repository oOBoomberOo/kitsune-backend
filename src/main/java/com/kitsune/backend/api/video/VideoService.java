package com.kitsune.backend.api.video;

import com.kitsune.backend.entity.Video;
import com.kitsune.backend.entity.VideoRepository;
import com.kitsune.backend.entity.VideoResponse;
import com.kitsune.backend.model.SearchRequest;
import com.kitsune.backend.model.VideoStatus;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    private static final List<VideoStatus> ACTIVE = List.of(
            VideoStatus.SCHEDULED,
            VideoStatus.ACTIVE
    );

    private static final List<VideoStatus> COMPLETED = List.of(
            VideoStatus.COMPLETED
    );

    private static final List<VideoStatus> ERROR = List.of(
            VideoStatus.PANIC
    );

    public Page<VideoResponse> findAllVideos(SearchRequest request) {
        return videoRepository.findAll(Video.search(request.getSearch()), request.toPageRequest("addedAt"))
                .map(VideoResponse::new);
    }

    public List<Video> findVideoToBeRecord() {
        log.info("Finding videos to be recorded");
        return videoRepository.findRunningVideos(ACTIVE);
    }

    public List<Video> findPanickedVideos() {
        log.info("Finding panicked videos");
        return videoRepository.findVideosByStatusIn(ERROR);
    }

    public VideoResponse getVideo(@NotNull String videoId) {
        return videoRepository.findById(videoId)
                .map(VideoResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
    }

    private Video getVideoById(@NotNull String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
    }

    public Video start(Video video) {
        video.setStatus(VideoStatus.ACTIVE);
        video.setPanicMessage(null);
        return videoRepository.save(video);
    }

    public void panic(Video video, String message) {
        video.setStatus(VideoStatus.PANIC);
        video.setPanicMessage(message);
        videoRepository.save(video);
    }

    public void finish(Video video) {
        video.setStatus(VideoStatus.COMPLETED);
        videoRepository.save(video);
    }

    public Mono<Video> uploadVideo(@NotNull VideoInfo info, @NotNull UploadVideoRequest request) {
        var startAt = Objects.requireNonNullElse(request.getStartAt(), info.getPublishDate());
        var video = Video.builder()
                .id(info.videoId)
                .type(info.type)
                .startAt(startAt)
                .endAt(request.getEndAt())
                .title(info.title)
                .build();

        return Mono.just(video)
                .publishOn(Schedulers.boundedElastic())
                .map(videoRepository::save);
    }

    public Mono<Video> startMono(Video video) {
        return Mono.just(video)
                .publishOn(Schedulers.boundedElastic())
                .map(this::start);
    }

    public Mono<VideoResponse> restartMono(String videoId) {
        return Mono.just(videoId)
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(id -> videoRepository.findById(id).orElse(null))
                .mapNotNull(video -> video.isExpired() ? null : video)
                .flatMap(this::startMono)
                .map(VideoResponse::new);
    }

    public Mono<VideoResponse> stopMono(String videoId) {
        return Mono.just(videoId)
                .publishOn(Schedulers.boundedElastic())
                .map(id -> {
                    var video = getVideoById(id);
                    video.setStatus(VideoStatus.COMPLETED);
                    video.setEndAt(LocalDateTime.now());
                    return new VideoResponse(videoRepository.save(video));
                });
    }
}
