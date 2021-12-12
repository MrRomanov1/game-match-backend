package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.model.game.GameMode;
import pl.gamematch.GameMatch.model.game.Platform;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findGamesByGameCategoriesName(String name);
    List<Game> findGamesByGameCategoriesIn (List <GameCategory> gameCategoryList);
    List<Game> findGamesByGameModesIn (List <GameMode> gameCategoryList);
    List<Game> findGamesByPlatformsIn (List <Platform> gameCategoryList);
}