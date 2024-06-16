package com.kitsune.backend.youtube;

import com.kitsune.backend.api.record.RecordService;
import com.kitsune.backend.api.video.VideoService;
import com.kitsune.backend.entity.Record;
import com.kitsune.backend.entity.Video;
import com.kitsune.backend.model.VideoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class YouTubeService {

    private final VideoService videoService;
    private final RecordService recordService;
    private final InvidiousService invidiousService;

    @Scheduled(cron = "0 */5 * * * *")
    public void fetchYouTube() {
        var videos = videoService.findVideoToBeRecord();
        log.info("Found {} videos to be recorded", videos.size());

        for (var video : videos) {
            uploadRecord(video);
        }
    }

    public void uploadRecord(Video video) {
        var timestamp = LocalDateTime.now();

        invidiousService.getVideoInfo(video.getId())
                .subscribe(info -> {
                    video.setType(info.getType());
                    video.setStatus(VideoStatus.ACTIVE);

                    if (video.isExpired()) {
                        video.setStatus(VideoStatus.COMPLETED);
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

}
