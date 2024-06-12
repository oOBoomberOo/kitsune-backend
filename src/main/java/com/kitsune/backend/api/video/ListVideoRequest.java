package com.kitsune.backend.api.video;

import com.kitsune.backend.model.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Schema
public class ListVideoRequest {
    String search = "";

    int page = 1;
    int pageSize = 100;

    SortOrder sortOrder = SortOrder.DESC;
}
