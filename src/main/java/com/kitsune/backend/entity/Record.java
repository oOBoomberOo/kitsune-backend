package com.kitsune.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

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
    @JsonIgnore
    Video video;
}
