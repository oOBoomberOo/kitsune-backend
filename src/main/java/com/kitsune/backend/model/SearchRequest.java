package com.kitsune.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
@Schema
public class SearchRequest {
    @Builder.Default
    String search = "";

    @Builder.Default
    int page = 1;
    @Builder.Default
    int pageSize = 100;

    @Builder.Default
    SortOrder sortOrder = SortOrder.DESC;

    public Sort toSort(String... properties) {
        return sortOrder.getSort(properties);
    }

    public PageRequest toPageRequest() {
        var pageRequest = PageRequest.of(page - 1, pageSize);

        if (pageRequest.getOffset() > Integer.MAX_VALUE) {
            return PageRequest.of(0, Integer.MAX_VALUE);
        }

        return pageRequest;
    }

    public PageRequest toPageRequest(String... properties) {
        var pageRequest = PageRequest.of(page - 1, pageSize, toSort(properties));

        if (pageRequest.getOffset() > Integer.MAX_VALUE) {
            return PageRequest.of(0, Integer.MAX_VALUE, toSort(properties));
        }

        return pageRequest;
    }
}
