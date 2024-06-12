package com.kitsune.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    /**
     * The discord tag of the user.
     */
    @Id
    String id;

    @Column
    String accessToken;

    @Column
    String refreshToken;
}
