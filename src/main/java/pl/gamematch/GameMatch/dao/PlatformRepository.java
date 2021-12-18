package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gamematch.GameMatch.model.game.Platform;

import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    List<Platform> findPlatformsByType(String name);
}
