package pl.gamematch.GameMatch.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gamematch.GameMatch.model.game.Game;

@RepositoryRestResource
public interface GameRepository extends JpaRepository<Game, Long> {
}