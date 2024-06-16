package com.kitsune.backend.entity;

import com.kitsune.backend.constant.PostgresTime;
import com.kitsune.backend.model.VideoStatus;
import com.kitsune.backend.model.VideoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "videos")
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
    VideoStatus status;

    @Column(nullable = false)
    @NotNull
    @Builder.Default
    LocalDateTime startAt = LocalDateTime.now();

    @Column(nullable = false)
    @NotNull
    @Builder.Default
    LocalDateTime endAt = PostgresTime.MAX;

    @Column
    @With
    String panicMessage;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endAt);
    }

    public static Specification<Video> search(String search) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("title")),
                "%" + search.toLowerCase() + "%"
        );
    }
}
