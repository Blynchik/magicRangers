package dev.blynchik.magicRangers.model.storage;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event",
        indexes = @Index(name = "idx_event_title", columnList = "title"))
@Data
@NoArgsConstructor
public class AppEvent {

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
    private List<AppEventOption> options;

    @Column(name = "created_at", columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default now()")
    private LocalDateTime updatedAt;

    public AppEvent(String title, String descr, List<AppEventOption> options) {
        this.title = title;
        this.descr = descr;
        this.options = options;
    }
}
