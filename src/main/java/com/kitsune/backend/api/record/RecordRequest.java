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

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
@Schema
public class RecordRequest {
    @Builder.Default
    private int interval = 5;

    @Builder.Default
    private OffsetDateTime after = PostgresTime.MIN;
    @Builder.Default
    private OffsetDateTime before = PostgresTime.MAX;

    @Builder.Default
    int page = 1;
    @Builder.Default
    int pageSize = 100;

    @Builder.Default
    SortOrder sortOrder = SortOrder.DESC;

    public Sort toSort(String properties) {
        return Sort.by(sortOrder.getSort(properties));
    }

    public PageRequest toPageRequest(String properties) {
        var pageRequest = PageRequest.of(page - 1, pageSize, toSort(properties));

        if (pageRequest.getOffset() > Integer.MAX_VALUE) {
            return PageRequest.of(0, Integer.MAX_VALUE, toSort(properties));
        }

        return pageRequest;
    }
}
