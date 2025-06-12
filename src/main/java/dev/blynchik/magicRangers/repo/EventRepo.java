package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    Optional<Event> findByEventTitle(String title);
}
