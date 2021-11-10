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
    public @ResponseBody Map<Game, Double> matchGamesToUserInput (@RequestBody ArrayList<GameCategory> gameCategories) {

        Map<GameCategory, Double> gameCategoryByRating = new HashMap<>();

        for (GameCategory category : gameCategories) {
            gameCategoryByRating.put(category, calculateCategoryRating(category));
        }

        /**@description first category gets 10x higher evaluation **/
        gameCategoryByRating.computeIfPresent(gameCategories.get(0),
                (key, val) -> val * 10);

        List<GameCategory> categoriesSortedByRatings = sortCategoriesByRatings(gameCategoryByRating);

        List<Game> gameList = gameRepository.findGamesByGameCategoriesIn(categoriesSortedByRatings);
        Set gameListWithoutDuplicates = Set.copyOf(gameList);

        List<String> categoryNamesToCompare = categoriesSortedByRatings
                .stream()
                .map(GameCategory::getName)
                .collect(Collectors.toList());

        return handleGameMatchingCalculations(gameListWithoutDuplicates, categoryNamesToCompare);
    }

    private Double calculateCategoryRating(GameCategory category) {
        return category.getRating() * category.getNumberOfVotes();
    }

    private List<GameCategory> sortCategoriesByRatings(Map<GameCategory, Double> unorderedCategories) {
        return unorderedCategories
                .entrySet().stream().sorted(Comparator
                        .comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<Game, Double> handleGameMatchingCalculations(Set<Game> gameList, List<String> categoryNames) {

        //TODO -> logika odpowiadająca za usuwanie z listy gier, które zostały dodane już do mapy
        //TODO -> logika odpowiadająca za zmianę procentu zmatchowania w przypadku wystąpienia więcej niż jednej gry
        //TODO -> logika odpowiadająca za sortowanie końcowych wyników po procencie zmatchowania (map.value)
        while (!categoryNames.isEmpty()) {
            if (!gameList.isEmpty()) {
                System.out.println("***********");
                Map <Game, Double> currentIterationGamesWithMatch = new HashMap<>();
                for (Game game : gameList) {
                    List<String> gameCategories = getSingleGameCategories(game);
                    if (gameCategories.containsAll(categoryNames)) {
                        System.out.println("tru");
                    }
                    System.out.println(game.getId());
                }
            }
            categoryNames.remove(categoryNames.size()-1);
        }

        return null;
    }

    private List<String> getSingleGameCategories(Game game) {
        Collection <GameCategory> gameCategories = game.getGameCategories();
        List<String> gameCategoryNames = new ArrayList<>();
        for (GameCategory gameCat : gameCategories) {
            gameCategoryNames.add(gameCat.getName());
        }
        return gameCategoryNames;
    }
}
