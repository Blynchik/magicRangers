package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepo extends JpaRepository<Character, Long> {

    Optional<Character> findByAppUserId(Long appUserId);

    Boolean existsByAppUserId(Long appUserId);

    Boolean existsByIdAndCurrentEventIsNotNull(Long characterId);

    @Modifying
    @Query(value = "UPDATE character SET current_event = cast(:event as jsonb), updated_at = now() WHERE id = :characterId", nativeQuery = true)
    int updateCurrentEvent(@Param("characterId") Long characterId, @Param("event") String eventJson);
}
