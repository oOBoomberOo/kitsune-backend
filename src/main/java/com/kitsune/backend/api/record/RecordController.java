package com.kitsune.backend.api.record;

import com.kitsune.backend.entity.Record;
import com.kitsune.backend.model.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@Tag(name = "Record API")
public class RecordController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<Record> listRecords(@Valid @RequestParam @ParameterObject RecordRequest request) {
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
