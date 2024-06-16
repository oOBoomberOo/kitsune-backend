package com.kitsune.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, updatable = false)
    Long views;

    @Column(nullable = false, updatable = false)
    @Generated(event = EventType.INSERT, sql = "now()")
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "video_id")
    @ToString.Exclude
    Video video;

    public static Specification<Record> videoIdEquals(String videoId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("video").get("id"), videoId);
    }

    public static Specification<Record> createdAtBetween(LocalDateTime after, LocalDateTime before) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdAt"), after, before);
    }

    public static Specification<Record> intervalEquals(int interval) {
        int intervalMinutes = interval * 60 * 1000;

        int upperBound = 15 * 1000;
        int lowerBound = intervalMinutes - upperBound;

        return (root, query, criteriaBuilder) -> {
            var modTime = criteriaBuilder.mod(root.get("createdAt").as(Integer.class), intervalMinutes);

            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(
                            modTime,
                            lowerBound
                    ),

                    criteriaBuilder.lessThanOrEqualTo(
                            modTime,
                            upperBound
                    )
            );
        };
    }
}
