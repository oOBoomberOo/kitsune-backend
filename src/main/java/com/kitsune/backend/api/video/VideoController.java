package com.kitsune.backend.api.video;

import com.kitsune.backend.api.record.ListRecordRequest;
import com.kitsune.backend.api.tracker.ListTrackerRequest;
import com.kitsune.backend.entity.Tracker;
import com.kitsune.backend.entity.Video;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Video> listVideos(@Valid @RequestParam @ParameterObject ListVideoRequest request) {
        throw new NotImplementedException();
    }

    @GetMapping(value = "/{videoId}/trackers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Tracker> listRecords(@PathVariable String videoId, @Valid @RequestParam @ParameterObject ListTrackerRequest request) {
        throw new NotImplementedException();
    }

    @GetMapping(value = "/{videoId}/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Record> listRecords(@PathVariable String videoId, @Valid @RequestParam @ParameterObject ListRecordRequest request) {
        throw new NotImplementedException();
    }


}
