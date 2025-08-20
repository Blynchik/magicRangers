package dev.blynchik.magicRangers.model.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @Email
    @Size(min = 4, max = 255)
    private String email;

    @Column(name = "provider")
    private String oauth2Provider;

    @Column(name = "sub")
    private String oauth2Sub;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "character_id")
    private Long characterId;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role",
            joinColumns = @JoinColumn(name = "app_user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"app_user_id", "role"}, name = "uc_user_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private Set<Role> roles;

    public AppUser(String oauth2Provider, String oauth2Sub, String email, Collection<Role> roles) {
        this.oauth2Provider = oauth2Provider;
        this.oauth2Sub = oauth2Sub;
        this.email = email;
        setRoles(roles);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getEffectiveClass() != getEffectiveClass(o)) return false;
        return getId() != null && getId().equals(((AppUser) o).getId());
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

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}
