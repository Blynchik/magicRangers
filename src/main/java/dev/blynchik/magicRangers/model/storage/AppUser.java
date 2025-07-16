package dev.blynchik.magicRangers.model.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "created_at", columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default now()")
    private LocalDateTime updatedAt;

    @Column(name = "character_id")
    private Long characterId;

    @Override
    public String toString() {
        return "AppUser: %s%s[%s]".formatted(oauth2Provider, oauth2Sub, email);
    }

    public AppUser(String oauth2Provider, String oauth2Sub, String email) {
        this.oauth2Provider = oauth2Provider;
        this.oauth2Sub = oauth2Sub;
        this.email = email;
    }
}
