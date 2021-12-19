package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gamematch.GameMatch.model.game.GameMode;

import java.util.List;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode, Long> {
    List<GameMode> findGameModesByName(String name);
}
