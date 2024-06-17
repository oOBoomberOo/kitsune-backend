package com.kitsune.backend.api.video;

import com.kitsune.backend.api.record.RecordRequest;
import com.kitsune.backend.api.record.RecordService;
import com.kitsune.backend.entity.RecordResponse;
import com.kitsune.backend.entity.Video;
import com.kitsune.backend.entity.VideoResponse;
import com.kitsune.backend.model.SearchRequest;
import com.kitsune.backend.youtube.YouTubeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final YouTubeService youTubeService;
    private final RecordService recordService;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<VideoInfo>> getVideoInfo(@RequestParam("videoId") List<String> videoIds) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(youTubeService::getVideoInfo)
                .collectList();
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Video>> addVideo(@RequestParam("videoId") List<String> videoIds, @Valid @RequestBody UploadVideoRequest request) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(youTubeService::getVideoInfo)
                .flatMap(videoInfo -> videoService.uploadVideo(videoInfo, request))
                .collectList();
    }

    @PostMapping(value = "/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<VideoResponse>> restartVideo(@RequestParam("videoId") List<String> videoIds) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(videoService::restartMono)
                .collectList();
    }

    @PostMapping(value = "/stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<VideoResponse>> stopVideo(@RequestParam("videoId") List<String> videoIds) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(videoService::stopMono)
                .collectList();
    }

    @GetMapping(value = "/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VideoResponse getVideo(@PathVariable String videoId) {
        return videoService.getVideo(videoId);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<VideoResponse> listVideos(@Valid @ParameterObject SearchRequest request) {
        return videoService.findAllVideos(request);
    }

    @GetMapping(value = "/{videoId}/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<RecordResponse> listRecords(@Valid @PathVariable String videoId, @ParameterObject RecordRequest request) {
        return recordService.findAllRecords(videoId, request);
    }

}
