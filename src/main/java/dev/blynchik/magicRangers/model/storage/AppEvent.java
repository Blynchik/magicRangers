package dev.blynchik.magicRangers.model.storage;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "event",
        indexes = @Index(name = "idx_event_title", columnList = "title"))
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public AppEvent(String title, String descr, List<AppEventOption> options) {
        this.title = title;
        this.descr = descr;
        this.options = options;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getEffectiveClass() != getEffectiveClass(o)) return false;
        return getId() != null && getId().equals(((AppEvent) o).getId());
    }

    @Override
    public final int hashCode() {
        return getEffectiveClass(this).hashCode();
    }

    private Class<?> getEffectiveClass() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : getClass();
    }

    private static Class<?> getEffectiveClass(Object o) {
        return o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    }
}
