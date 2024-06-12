package com.kitsune.backend.api.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitsune.backend.model.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
@Schema
public class ListRecordRequest {
    List<String> trackers;

    int page = 1;
    int pageSize = 100;

    SortOrder sortOrder = SortOrder.DESC;
}
