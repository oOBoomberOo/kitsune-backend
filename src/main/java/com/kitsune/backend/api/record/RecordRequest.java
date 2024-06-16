package com.kitsune.backend.api.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitsune.backend.constant.PostgresTime;
import com.kitsune.backend.model.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
@Schema
public class RecordRequest {
    @Builder.Default
    int interval = 5;

    @Builder.Default
    LocalDateTime after = PostgresTime.MIN;
    @Builder.Default
    LocalDateTime before = PostgresTime.MAX;

    @Builder.Default
    int page = 1;
    @Builder.Default
    int pageSize = 100;

    @Builder.Default
    SortOrder sortOrder = SortOrder.DESC;

    public Sort toSort(String... properties) {
        return sortOrder.getSort(properties);
    }

    public PageRequest toPageRequest(String... properties) {
        return PageRequest.of(page - 1, pageSize, toSort(properties));
    }
}
