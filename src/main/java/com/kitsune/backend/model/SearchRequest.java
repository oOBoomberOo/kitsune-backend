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
        return PageRequest.of(page - 1, pageSize);
    }

    public PageRequest toPageRequest(String... properties) {
        return PageRequest.of(page - 1, pageSize, toSort(properties));
    }
}
