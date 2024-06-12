package com.kitsune.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "videos")
public class Video {
    @Id
    String id;

    @Column
    String title;

    @Column
    LocalDateTime addedAt;
}
