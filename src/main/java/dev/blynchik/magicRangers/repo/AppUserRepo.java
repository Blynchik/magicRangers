package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.AppUser;
import dev.blynchik.magicRangers.model.storage.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<AppUser> findByEmailIgnoreCase(String email);

    Optional<AppUser> findByOauth2ProviderAndOauth2Sub(String oauth2Provider, String oauth2Sub);

    @Query("SELECT u.id FROM AppUser u " +
            "JOIN u.roles r " +
            "WHERE r = :role " +
            "AND u.createdAt <= :thresholdTime")
    List<Long> findUserIdsByRoleAndCreatedBefore(@Param("role") Role role,
                                                 @Param("thresholdTime") Instant thresholdTime);
}
