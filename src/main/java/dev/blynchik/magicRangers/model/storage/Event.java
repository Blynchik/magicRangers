package dev.blynchik.magicRangers.model.storage;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @Type(JsonBinaryType.class)
    @Column(name = "option_collection", columnDefinition = "jsonb")
    @Size(min = 1, max = 10)
    private Set<EventOption> options;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Event(String title, String descr, Set<EventOption> options, LocalDateTime createdAt) {
        this.title = title;
        this.descr = descr;
        this.options = options;
        this.createdAt = createdAt;
    }
}
