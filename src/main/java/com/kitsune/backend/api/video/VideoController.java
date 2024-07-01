package com.kitsune.backend.api.video;

import com.kitsune.backend.api.record.RecordRequest;
import com.kitsune.backend.api.record.RecordService;
import com.kitsune.backend.entity.RecordResponse;
import com.kitsune.backend.entity.Video;
import com.kitsune.backend.entity.VideoResponse;
import com.kitsune.backend.error.RaceException;
import com.kitsune.backend.model.PageResponse;
import com.kitsune.backend.model.SearchRequest;
import com.kitsune.backend.youtube.YouTubeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
@Tag(name = "Video API")
public class VideoController {

    private final VideoService videoService;
    private final YouTubeService youTubeService;
    private final RecordService recordService;

    @Operation(summary = "Get video info", description = "Retrieve video information from Invidious and Holodex")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved info about the videos"),
            @ApiResponse(responseCode = "400", description = "One or more video could not be found", content = @Content(schema = @Schema(implementation = RaceException.class)))
    })
    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<VideoInfo>> getVideoInfo(@RequestParam("videoId") List<String> videoIds) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(youTubeService::getVideoInfo)
                .collectList();
    }

    @Operation(summary = "Track videos", description = "Add video to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added videos"),
            @ApiResponse(responseCode = "400", description = "Could not look up information of one or more videos", content = @Content(schema = @Schema(implementation = RaceException.class)))
    })
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Video>> addVideo(@RequestParam("videoId") List<String> videoIds, @Valid @RequestBody UploadVideoRequest request) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(youTubeService::getVideoInfo)
                .flatMap(videoInfo -> videoService.uploadVideo(videoInfo, request))
                .collectList();
    }

    @Operation(summary = "Restart videos", description = "Restart videos that are stopped")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully restarted videos")
    })
    @PostMapping(value = "/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<VideoResponse>> restartVideo(@RequestParam("videoId") List<String> videoIds) {
        return Flux.fromIterable(videoIds)
                .map(youTubeService::parseVideoId)
                .flatMap(videoService::restartMono)
                .collectList();
    }

    @Operation(summary = "Stop videos", description = "Stop videos that are active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully stopped videos")
    })
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

    @Operation(summary = "List videos", description = "List videos in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved videos")
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<VideoResponse> listVideos(@Valid @ParameterObject SearchRequest request) {
        var page = videoService.findAllVideos(request);
        return new PageResponse<>(page);
    }

    @Operation(summary = "List records", description = "List records of a video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved records")
    })
    @GetMapping(value = "/{videoId}/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<RecordResponse> listRecords(@Valid @PathVariable String videoId, @ParameterObject RecordRequest request) {
        var page = recordService.findAllRecords(videoId, request);
        return new PageResponse<>(page);
    }

}
