package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<AppUser> findByEmailIgnoreCase(String email);

    Optional<AppUser> findByOauth2ProviderAndOauth2Sub(String oauth2Provider, String oauth2Sub);
}
