package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.*;
import pl.gamematch.GameMatch.dao.GameRepository;
import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.service.GameService;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/games-by-category/{name}")
    public List<Game> getGamesByCategory(@PathVariable String name) {
        return gameRepository.findGamesByGameCategoriesName(name);
    }

    @GetMapping("/games/all")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @PostMapping("/match")
    public @ResponseBody List<Game> matchGamesToUserInput (@RequestBody ArrayList<GameCategory> gameCategories) {
        List <Game> gameList = new ArrayList<>();
        Map<GameCategory, Double> gameCategoryByRating = new HashMap<>();

        for (GameCategory category : gameCategories) {
            gameCategoryByRating.put(category, calculateCategoryRating(category));
        }

        /**@description first category gets 10x higher evaluation **/
        gameCategoryByRating.computeIfPresent(gameCategories.get(0),
                (key, val) -> val * 10);

        List<GameCategory> categoriesSortedByRatings = gameCategoryByRating
                .entrySet().stream().sorted(Comparator
                        .comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return gameList;
    }

    private Double calculateCategoryRating(GameCategory category) {
        return category.getRating() * category.getNumberOfVotes();
    }
}
