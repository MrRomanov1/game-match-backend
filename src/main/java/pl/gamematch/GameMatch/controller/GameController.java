package pl.gamematch.GameMatch.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.gamematch.GameMatch.Utils;
import pl.gamematch.GameMatch.dao.GameRepository;
import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.service.GameService;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @PostMapping(path = "/match", produces = MediaType.APPLICATION_JSON_VALUE)
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

        Map <Game, Double> gamesWithMatch = new HashMap<>();
        List<Game> gamesAlreadyAddedToMap = new ArrayList<>();
        int numberOfInsertedCategories = categoryNames.size();
        int whileCounterToCalculateMatch = 0;

        while (!categoryNames.isEmpty()) {
            if (!gameList.isEmpty()) {
                Map <Game, Double> currentIterationGamesWithMatch = new HashMap<>();

                for (Game game : gameList) {
                    List<String> gameCategories = getSingleGameCategories(game);

                    if (gameCategories.containsAll(categoryNames) && !gamesAlreadyAddedToMap.contains(game)) {
                        currentIterationGamesWithMatch.put(game,
                                calculateCurrentGameMatch(numberOfInsertedCategories,
                                        whileCounterToCalculateMatch));
                        gamesAlreadyAddedToMap.add(game);
                    }
                }
                if (currentIterationGamesWithMatch.size() > 1) {
                    gamesWithMatch.putAll(calculateGameMatchByRating(
                            currentIterationGamesWithMatch,
                            numberOfInsertedCategories,
                            whileCounterToCalculateMatch));
                }
                else if (currentIterationGamesWithMatch.size() == 1){
                    gamesWithMatch.putAll(currentIterationGamesWithMatch);
                }
            }
            categoryNames.remove(categoryNames.size()-1);
            whileCounterToCalculateMatch++;
        }

        return Utils.sortByValue(gamesWithMatch);
    }

    private List<String> getSingleGameCategories(Game game) {
        Collection <GameCategory> gameCategories = game.getGameCategories();
        List<String> gameCategoryNames = new ArrayList<>();
        for (GameCategory gameCat : gameCategories) {
            gameCategoryNames.add(gameCat.getName());
        }
        return gameCategoryNames;
    }

    private Double calculateCurrentGameMatch(int numberOfInsertedCategories, int whileCounterToCalculateMatch) {
        Double currentGameMatch = ((numberOfInsertedCategories - whileCounterToCalculateMatch)  * 100d / numberOfInsertedCategories);

        return BigDecimal.valueOf(currentGameMatch)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Double calculateNewGameMatch (Double currentGroupMatchValue, Double matchValueRange, int mapIterator, int mapSize) {
        Double newGameMatch = currentGroupMatchValue - (matchValueRange / mapSize) * mapIterator;

        return BigDecimal.valueOf(newGameMatch)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Map <Game, Double> calculateGameMatchByRating(Map <Game, Double> gamesWithMatchToRecalculate, int numberOfCategories, int numberOfIterations) {
        Map <Game, Double> gamesWithNewMatchValues = new HashMap<>();
        Double currentGroupMatchValue = gamesWithMatchToRecalculate.entrySet().iterator().next().getValue();
        if (currentGroupMatchValue >= 50) {
            Double nextGroupMatchValue = 0d;
            if ((numberOfCategories - numberOfIterations - 1) != 0) {
                nextGroupMatchValue = (numberOfCategories / (numberOfCategories - numberOfIterations - 1)) * 100d;
            }
            Double matchValueRange = currentGroupMatchValue - nextGroupMatchValue;
            Map <Game, Double> gamesValuedByRatings = new HashMap<>();
            for (Map.Entry <Game, Double> gameByMatch : gamesWithMatchToRecalculate.entrySet()) {
                gamesValuedByRatings.put(gameByMatch.getKey(), gameByMatch.getKey().getRating() * gameByMatch.getKey().getNumberOfVotes());
            }
            Map <Game, Double> gamesValuedByRatingsSorted = Utils.sortByValue(gamesValuedByRatings);

            int mapIterator = 0;
            for (Map.Entry <Game, Double> gameByRating : gamesValuedByRatingsSorted.entrySet()) {
                gamesWithNewMatchValues.put(gameByRating.getKey(), calculateNewGameMatch(currentGroupMatchValue, matchValueRange, mapIterator, gamesWithMatchToRecalculate.size()));
                mapIterator++;
            }
            return gamesWithNewMatchValues;
        }
        return gamesWithMatchToRecalculate;
    }
}
