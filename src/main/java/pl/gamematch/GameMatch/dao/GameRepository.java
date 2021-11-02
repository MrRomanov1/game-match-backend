package pl.gamematch.GameMatch.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gamematch.GameMatch.model.game.Game;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    //@Query("SELECT games FROM Game game JOIN GameCategory gameCategory WHERE gameCategory.name =: categoryId")
    List<Game> findGamesByGameCategoriesName(String name);

}