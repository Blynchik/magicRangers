package dev.blynchik.magicRangers.model.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "event",
        indexes = @Index(name = "idx_event_title", columnList = "title"))
@Data
@NoArgsConstructor
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    @Size(min = 1, max = 255)
    private String title;

    @Column(name = "descr")
    @Size(min = 1, max = 1000)
    private String descr;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Event(String title, String descr, LocalDateTime createdAt) {
        this.title = title;
        this.descr = descr;
        this.createdAt = createdAt;
    }
}
