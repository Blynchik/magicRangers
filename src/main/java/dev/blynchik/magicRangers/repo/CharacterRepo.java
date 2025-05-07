package dev.blynchik.magicRangers.repo;

import dev.blynchik.magicRangers.model.storage.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepo extends JpaRepository<Character, Long> {

    Optional<Character> findByAppUserId(Long appUserId);
}
