package com.kitsune.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Builder
@Jacksonized
@JsonInclude
public record PageResponse<T>(@JsonIgnore Page<T> page) {

    @NotNull
    @JsonProperty
    public List<T> getContent() {
        return page.getContent();
    }

    @NotNull
    @JsonProperty("page")
    public PageMetadata getMetadata() {
        return new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }


    public static <T> PageResponse<T> of(List<T> content, Pageable pageable, long total) {
        return new PageResponse<>(new PageImpl<>(content, pageable, total));
    }

    public record PageMetadata(@NotNull long size, @NotNull long number, @NotNull long totalElements,
                               @NotNull long totalPages) {

    }
}
