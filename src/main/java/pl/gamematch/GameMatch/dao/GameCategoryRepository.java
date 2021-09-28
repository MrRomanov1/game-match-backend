package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.gamematch.GameMatch.model.game.GameCategory;

@RepositoryRestResource(collectionResourceRel = "gameCategoryRepository", path = "game-categories")
public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {

}
