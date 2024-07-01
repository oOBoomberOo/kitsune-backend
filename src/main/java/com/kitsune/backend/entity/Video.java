package com.kitsune.backend.entity;

import com.kitsune.backend.constant.PostgresTime;
import com.kitsune.backend.model.VideoStatus;
import com.kitsune.backend.model.VideoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "videos")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Video {
    @Id
    String id;

    @Column
    @Builder.Default
    String title = "";

    @Generated(event = EventType.INSERT, sql = "now()")
    @Column(nullable = false, updatable = false)
    LocalDateTime addedAt;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    VideoType type;

    @Column(nullable = false)
    @NotNull
    @With
    @Enumerated(EnumType.STRING)
    @Builder.Default
    VideoStatus status = VideoStatus.SCHEDULED;

    @Column(nullable = false)
    @NotNull
    @Builder.Default
    OffsetDateTime startAt = OffsetDateTime.now();

    @Column(nullable = false)
    @NotNull
    @Builder.Default
    OffsetDateTime endAt = PostgresTime.MAX;

    @Column
    @With
    String panicMessage;

    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(endAt);
    }

    public static Specification<Video> search(String search) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("title")),
                "%" + search.toLowerCase() + "%"
        );
    }

    public static Specification<Video> hasStatus(List<VideoStatus> status) {
        if (status.isEmpty()) {
            return (root, query, builder) -> builder.conjunction();
        }

        return (root, query, builder) -> root.get("status").in(status);
    }
}
