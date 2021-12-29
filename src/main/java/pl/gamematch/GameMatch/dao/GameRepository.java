package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gamematch.GameMatch.model.game.*;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByAlias (String alias);
    List<Game> findGamesByGameCategoriesAlias(String name);
    List<Game> findGamesByGameModesName (String gameModeName);
    List<Game> findGamesByPlatformsType(String platformType);
    List<Game> findGamesByGameCategoriesIn (List <GameCategory> gameCategoryList);
    List<Game> findGamesByGameModesIn (List <GameMode> gameModeList);
    List<Game> findGamesByPlatformsIn (List <Platform> platformList);
    List<Game> findGamesByThemesIn (List <Theme> platformList);
}