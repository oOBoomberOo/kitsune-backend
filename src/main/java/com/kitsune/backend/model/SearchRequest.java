package com.kitsune.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@JsonInclude
@Schema
public class SearchRequest {
    @Builder.Default
    private String search = "";

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int pageSize = 100;

    @Builder.Default
    private SortOrder sortOrder = SortOrder.DESC;

    @Builder.Default
    @ArraySchema(schema = @Schema(implementation = VideoStatus.class))
    private List<VideoStatus> status = List.of(VideoStatus.values());

    public Sort toSort(String properties) {
        var noErrorFirst = Sort.Order.desc("panicMessage");
        var byProperty = sortOrder.getSort(properties);
        return Sort.by(noErrorFirst, byProperty);
    }

    public PageRequest toPageRequest(String properties) {
        var pageRequest = PageRequest.of(page - 1, pageSize, toSort(properties));

        if (pageRequest.getOffset() > Integer.MAX_VALUE) {
            return PageRequest.of(0, Integer.MAX_VALUE, toSort(properties));
        }

        return pageRequest;
    }
}
