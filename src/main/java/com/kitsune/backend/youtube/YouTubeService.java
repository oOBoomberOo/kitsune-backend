package com.kitsune.backend.youtube;

import com.kitsune.backend.api.record.RecordService;
import com.kitsune.backend.api.video.VideoInfo;
import com.kitsune.backend.api.video.VideoService;
import com.kitsune.backend.entity.Record;
import com.kitsune.backend.entity.Video;
import com.kitsune.backend.model.VideoType;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class YouTubeService {

    private final VideoService videoService;
    private final RecordService recordService;
    private final InvidiousService invidiousService;
    private final HolodexService holodexService;

    @Scheduled(cron = "${config.timing.schedule}")
    public void fetchYouTube() {
        var videos = videoService.findVideoToBeRecord();
        log.info("Found {} videos to be recorded", videos.size());

        for (var video : videos) {
            uploadRecord(video);
        }
    }

    public void uploadRecord(Video video) {
        var timestamp = OffsetDateTime.now();

        getVideoInfo(video.getId())
                .subscribe(info -> {
                    video.setType(info.getType());

                    if (video.isExpired()) {
                        videoService.finish(video);
                    } else {
                        videoService.start(video);
                    }

                    var record = Record.builder()
                            .video(video)
                            .views(info.getViews())
                            .createdAt(timestamp)
                            .build();

                    recordService.upload(record);
                }, error -> {
                    log.error("Failed to fetch video info for {}", video.getId(), error);
                    videoService.panic(video, error.getMessage());
                });
    }


    public Mono<VideoInfo> getVideoInfo(@NotNull String videoId) {
        return invidiousService.getVideo(videoId)
                .zipWith(holodexService.getVideo(videoId))
                .map(this::mapVideoInfo);
    }

    private VideoInfo mapVideoInfo(Tuple2<InvidiousVideo, HolodexVideo> tuple) {
        var invidious = tuple.getT1();
        var holodex = tuple.getT2();

        var videoType = getVideoType(invidious);
        var publishDate = holodex.getScheduledTime();
        var views = invidious.isLiveNow() ? holodex.getLiveViewers() : invidious.getViewCount();

        return VideoInfo.builder()
                .videoId(invidious.getVideoId())
                .type(videoType)
                .title(invidious.getTitle())
                .author(invidious.getAuthor())
                .description(invidious.getDescription())
                .uploadDate(invidious.getPublished())
                .publishDate(publishDate)
                .views(views)
                .build();
    }

    public VideoType getVideoType(InvidiousVideo video) {
        if (video.isUpcoming()) {
            return VideoType.UPCOMING;
        }

        if (video.isLiveNow() || video.isPostLiveDvr()) {
            return VideoType.LIVE;
        }

        if (video.isPremiere()) {
            return VideoType.PREMIERE;
        }

        if (video.isPaid()) {
            return VideoType.PRIVATE;
        }

        return VideoType.UPLOAD;
    }

    public String parseVideoId(@NotNull String text) {
        try {
            var url = new URI(URLDecoder.decode(text, StandardCharsets.UTF_8));

            var host = url.getHost();
            var path = url.getPath();

            if (host == null) {
                return text;
            }

            // https://www.youtube.com/watch?v={videoId}
            if (host.endsWith("www.youtube.com") && path.equals("/watch")) {
                var query = url.getQuery();
                var params = query.split("&");

                for (var param : params) {
                    var pair = param.split("=");
                    if (pair[0].equals("v")) {
                        return pair[1];
                    }
                }
            }

            // https://youtu.be/{videoId}
            //noinspection SpellCheckingInspection
            if (host.endsWith("youtu.be")) {
                return path.substring(1);
            }

            // https://youtube.com/live/{videoId}
            if (host.endsWith("youtube.com") && path.startsWith("/live")) {
                return path.substring(6);
            }

            // https://youtube.com/shorts/{videoId}
            if (host.endsWith("youtube.com") && path.startsWith("/shorts")) {
                return path.substring(8);
            }

            // https://holodex.net/watch/{videoId}
            if (host.endsWith("holodex.net") && path.startsWith("/watch")) {
                return path.substring(7);
            }

            return text;
        } catch (URISyntaxException e) {
            return text;
        }
    }
}
