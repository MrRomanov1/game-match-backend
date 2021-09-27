package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.gamematch.GameMatch.model.game.Game;

@RepositoryRestResource(collectionResourceRel = "gameRepository", path = "games")
public interface GameRepository extends JpaRepository<Game, Long> {
}
