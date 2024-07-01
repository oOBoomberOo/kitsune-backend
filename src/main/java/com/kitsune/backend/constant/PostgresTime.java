package com.kitsune.backend.constant;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class PostgresTime {
    public static final OffsetDateTime MIN = LocalDateTime.of(0, 1, 1, 0, 0)
            .atOffset(ZoneOffset.UTC);
    public static final OffsetDateTime MAX = LocalDateTime.of(10000, 1, 1, 0, 0)
            .atOffset(ZoneOffset.UTC);
}
