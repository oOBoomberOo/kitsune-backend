package com.kitsune.backend.api.record;

import com.kitsune.backend.entity.Record;
import com.kitsune.backend.entity.RecordRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public void upload(Record record) {
        log.info("Uploading record: {}", record);
        recordRepository.save(record);
    }

    public Page<Record> findAllRecords(@NotNull String videoId, @NotNull RecordRequest request) {

        if (request.getInterval() < 1) {
            throw new IllegalArgumentException("Interval must be greater than 0");
        }

        var spec = Specification.allOf(
                Record.videoIdEquals(videoId),
                Record.createdAtBetween(request.getAfter(), request.getBefore()),
                Record.intervalEquals(request.getInterval())
        );

        return recordRepository.findAll(spec, request.toPageRequest("createdAt"));
    }
}
