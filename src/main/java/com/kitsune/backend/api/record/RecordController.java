package com.kitsune.backend.api.record;

import com.kitsune.backend.entity.Record;
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
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Record> listRecords(@Valid @RequestParam @ParameterObject ListRecordRequest request) {
        throw new NotImplementedException();
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Record createRecord(@Valid @RequestBody Record record) {
        throw new NotImplementedException();
    }

    @GetMapping(value = "/{recordId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Record getRecord(@PathVariable String recordId) {
        throw new NotImplementedException();
    }

}
