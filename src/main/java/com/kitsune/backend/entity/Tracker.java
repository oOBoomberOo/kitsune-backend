package com.kitsune.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "trackers")
public class Tracker {

    @Id
    UUID id;

    @Column
    LocalDateTime createdAt;

    @Column
    LocalDateTime startAt;

    @Column
    LocalDateTime endAt;

    @ManyToOne
    @JoinColumn(name = "video_id")
    Video video;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User createdBy;

}
