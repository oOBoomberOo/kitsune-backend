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
@Table(name = "records")
public class Record {
    @Id
    UUID id;

    @Column
    Long views;

    @Column
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "video_id")
    @ToString.Exclude
    Video video;
}
