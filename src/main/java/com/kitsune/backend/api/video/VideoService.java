package com.kitsune.backend.api.video;

import com.kitsune.backend.constant.PostgresTime;
import com.kitsune.backend.entity.Video;
import com.kitsune.backend.entity.VideoRepository;
import com.kitsune.backend.entity.VideoResponse;
import com.kitsune.backend.model.SearchRequest;
import com.kitsune.backend.model.VideoStatus;
import com.kitsune.backend.youtube.InvidiousService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final InvidiousService invidiousService;

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

    public VideoResponse uploadVideo(@Valid UploadVideoRequest request) {
        var videoInfo = invidiousService.getVideoInfo(request.getVideoId()).blockOptional()
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        var startAt = Objects.requireNonNullElse(request.getStartAt(), videoInfo.getPublishDate());
        var endAt = Objects.requireNonNullElse(request.getEndAt(), PostgresTime.MAX);

        var video = Video.builder()
                .id(request.getVideoId())
                .type(videoInfo.type)
                .status(VideoStatus.SCHEDULED)
                .startAt(startAt)
                .endAt(endAt)
                .title(videoInfo.title)
                .build();

        return new VideoResponse(videoRepository.save(video));
    }

    public void start(Video video) {
        video.setStatus(VideoStatus.ACTIVE);
        videoRepository.save(video);
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

    public VideoResponse restart(String videoId) {
        var video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        if (video.isExpired()) {
            return null;
        }

        start(video);
        return new VideoResponse(video);
    }
}
