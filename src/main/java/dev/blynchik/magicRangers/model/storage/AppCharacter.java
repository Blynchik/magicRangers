package dev.blynchik.magicRangers.model.storage;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;

@Entity
@Table(name = "character")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppCharacter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_user_id", unique = true)
    @Positive
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

    @Column(name = "created_at", columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default now()")
    private LocalDateTime updatedAt;

    @Type(JsonBinaryType.class)
    @Column(name = "current_event", columnDefinition = "jsonb")
    private AppEvent currentEvent;

    public AppCharacter(Long appUserId, String name, Integer str, Integer intl, Integer cha) {
        this.appUserId = appUserId;
        this.name = name;
        this.str = str;
        this.intl = intl;
        this.cha = cha;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getEffectiveClass() != getEffectiveClass(o)) return false;
        return getId() != null && getId().equals(((AppCharacter) o).getId());
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
