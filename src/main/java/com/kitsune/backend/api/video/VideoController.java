package com.kitsune.backend.api.video;

import com.kitsune.backend.api.record.RecordRequest;
import com.kitsune.backend.api.record.RecordService;
import com.kitsune.backend.entity.RecordResponse;
import com.kitsune.backend.entity.VideoResponse;
import com.kitsune.backend.model.SearchRequest;
import com.kitsune.backend.youtube.InvidiousService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final InvidiousService invidiousService;
    private final RecordService recordService;

    @GetMapping(value = "/{videoId}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<VideoInfo> getVideoInfo(@PathVariable String videoId) {
        return invidiousService.getVideoInfo(videoId);
    }

    @GetMapping(value = "/{videoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VideoResponse getVideo(@PathVariable String videoId) {
        return videoService.getVideo(videoId);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public VideoResponse addVideo(@Valid @RequestBody UploadVideoRequest request) {
        return videoService.uploadVideo(request);
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
