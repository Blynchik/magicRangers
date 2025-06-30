package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    Optional<Event> findByTitle(String title);

    @Query(value = "SELECT * FROM event ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Event> findRandom();
}
