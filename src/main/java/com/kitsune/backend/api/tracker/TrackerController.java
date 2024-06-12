package com.kitsune.backend.api.tracker;

import com.kitsune.backend.entity.Tracker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/trackers")
@RequiredArgsConstructor
public class TrackerController {


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Tracker> listTrackers(@Valid @RequestParam @ParameterObject ListTrackerRequest request) {
        throw new NotImplementedException();
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Tracker createTracker(@RequestBody @Valid Tracker tracker) {
        throw new NotImplementedException();
    }

    @GetMapping(value = "/{trackerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Tracker getTracker(@PathVariable String trackerId) {
        throw new NotImplementedException();
    }

    @PutMapping(value = "/{trackerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Tracker updateTracker(@PathVariable String trackerId, @RequestBody @Valid Tracker tracker) {
        throw new NotImplementedException();
    }

    @DeleteMapping(value = "/{trackerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Tracker stopTracker(@PathVariable String trackerId) {
        throw new NotImplementedException();
    }

}
