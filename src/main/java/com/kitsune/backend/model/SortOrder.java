package com.kitsune.backend.model;

import org.springframework.data.domain.Sort;

public enum SortOrder {
    ASC,
    DESC;

    public Sort getSort(String... properties) {
        var direction = this == ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, properties);
    }
}
