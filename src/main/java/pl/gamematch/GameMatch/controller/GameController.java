package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.dao.GameRepository;
import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.service.GameService;

import java.util.List;

@RestController
public class GameController {

    private GameService gameService;
    private GameRepository gameRepository;

    public GameController(GameService gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games/{id}")
    public Game getGame(@PathVariable Long id) {
        if (gameRepository.findById(id).isPresent()) {
            return gameRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @GetMapping("/games/all")
    public List<Game> getGames() {
        return gameRepository.findAll();
    }
}
