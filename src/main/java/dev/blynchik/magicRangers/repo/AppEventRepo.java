package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.AppEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppEventRepo extends JpaRepository<AppEvent, Long> {

    Optional<AppEvent> findByTitle(String title);

    @Query(value = "SELECT * FROM event ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<AppEvent> findRandom();

    Boolean existsByTitle(String title);
}
