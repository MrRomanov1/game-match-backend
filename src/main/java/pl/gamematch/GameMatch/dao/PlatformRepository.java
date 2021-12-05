package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gamematch.GameMatch.model.game.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
