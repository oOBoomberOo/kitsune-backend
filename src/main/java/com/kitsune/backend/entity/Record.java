package com.kitsune.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "records")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, updatable = false)
    Long views;

    @Column(nullable = false, updatable = false)
    OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "video_id")
    @ToString.Exclude
    @JsonIgnore
    Video video;
}
