package pl.gamematch.GameMatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gamematch.GameMatch.model.game.GameCategory;

import java.util.List;

@Repository
public interface GameCategoryRepository extends JpaRepository<GameCategory, Long> {

    List<GameCategory> findGameCategoriesByNameIn(List <String> gameCategoryList);
}
