package dev.blynchik.magicRangers.model.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "character")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_user_id", unique = true)
    private Long appUserId;

    @Column(name = "name")
    @Size(min = 1, max = 255)
    private String name;

    @Column(name = "str")
    @PositiveOrZero
    private Integer str;

    @Column(name = "intl")
    @PositiveOrZero
    private Integer intl;

    @Column(name = "cha")
    @PositiveOrZero
    private Integer cha;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Character: %s of %s with %s/%s/%s:%s/%s".formatted(name, appUserId, str, intl, cha, createdAt, updatedAt);
    }

    public Character(Long appUserId, String name, Integer str, Integer intl, Integer cha, LocalDateTime createdAt) {
        this.appUserId = appUserId;
        this.name = name;
        this.str = str;
        this.intl = intl;
        this.cha = cha;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }
}
