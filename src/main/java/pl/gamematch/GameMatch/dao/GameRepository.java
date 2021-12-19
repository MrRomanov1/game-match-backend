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

    List<Game> findGamesByGameCategoriesAlias(String name);
    List<Game> findGamesByGameModesName (String gameModeName);
    List<Game> findGamesByPlatformsType(String platformType);
    List<Game> findGamesByGameCategoriesIn (List <GameCategory> gameCategoryList);
    List<Game> findGamesByGameModesIn (List <GameMode> gameModeList);
    List<Game> findGamesByPlatformsIn (List <Platform> platformList);
}