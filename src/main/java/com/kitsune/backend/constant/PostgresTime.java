package com.kitsune.backend.constant;

import java.time.LocalDateTime;

public class PostgresTime {
    public static final LocalDateTime MIN = LocalDateTime.of(0, 1, 1, 0, 0);
    public static final LocalDateTime MAX = LocalDateTime.of(10000, 1, 1, 0, 0);
}
