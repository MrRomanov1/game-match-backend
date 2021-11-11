package pl.gamematch.GameMatch.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.gamematch.GameMatch.utils.Utils;
import pl.gamematch.GameMatch.dao.GameRepository;
import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.service.GameService;
import pl.gamematch.GameMatch.utils.GameCategoryUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Piotr Romanczak on 01-11-2021
 * Description: GameController class
 */

@RestController
public class GameController {

    private GameService gameService;
    private GameRepository gameRepository;

    public GameController(GameService gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
    }

    /**
     * Created by Piotr Romanczak on 01-11-2021
     * Description: this method returns a Game object by provided Id
     * @param id
     * @return Game
     */
    @GetMapping("/games/{id}")
    public Game getGame(@PathVariable Long id) {
        if (gameRepository.findById(id).isPresent()) {
            return gameRepository.findById(id).get();
        } else {
            return null;
        }
    }

    /**
     * Created by Piotr Romanczak on 02-11-2021
     * Description: this method returns List of games by provided category name
     * @param name
     * @return List<Game>
     */
    @GetMapping("/games-by-category/{name}")
    public List<Game> getGamesByCategory(@PathVariable String name) {
        return gameRepository.findGamesByGameCategoriesName(name);
    }

    /**
     * Created by Piotr Romanczak on 02-11-2021
     * Description: this method returns List of all games
     * @return List<Game>
     */
    @GetMapping("/games/all")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Created by Piotr Romanczak on 08-11-2021
     * Description: this method returns List of all games by provided GameCategory List
     * @param gameCategories
     * @return List<Game>
     */
    @PostMapping(path = "/match", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Game> matchGamesToUserInput (@RequestBody ArrayList<GameCategory> gameCategories) {

        Map<GameCategory, Double> gameCategoryByRating = new HashMap<>();

        for (GameCategory category : gameCategories) {
            gameCategoryByRating.put(category, category.calculateCategoryRating());
        }

        /**@description first category gets 10x higher evaluation **/
        gameCategoryByRating.computeIfPresent(gameCategories.get(0),
                (key, val) -> val * 10);

        List<GameCategory> categoriesSortedByRatings = GameCategoryUtils.sortCategoriesByRatings(gameCategoryByRating);

        List<Game> gameList = gameRepository.findGamesByGameCategoriesIn(categoriesSortedByRatings);
        Set gameListWithoutDuplicates = Set.copyOf(gameList);

        List<String> categoryNamesToCompare = categoriesSortedByRatings
                .stream()
                .map(GameCategory::getName)
                .collect(Collectors.toList());

        return handleGameMatchingCalculations(gameListWithoutDuplicates, categoryNamesToCompare);
    }

    /**
     * Created by Piotr Romanczak on 10-11-2021
     * Description: this method calculates gameMatch field of all games
     * @param gameList
     * @param categoryNames
     * @return List<Game>
     */
    private List<Game> handleGameMatchingCalculations(Set<Game> gameList, List<String> categoryNames) {

        List <Game> gamesWithMatch = new ArrayList<>();
        List <Game> gamesAlreadyAdded = new ArrayList<>();
        int numberOfInsertedCategories = categoryNames.size();
        int whileCounterToCalculateMatch = 0;

        while (!categoryNames.isEmpty()) {
            if (!gameList.isEmpty()) {
                List <Game> currentIterationGamesWithMatch = new ArrayList<>();

                for (Game game : gameList) {
                    List<String> gameCategories = game.getSingleGameCategoriesNames();

                    if (gameCategories.containsAll(categoryNames) && !gamesAlreadyAdded.contains(game)) {
                        game.setGameMatch(calculateCurrentGameMatch(
                                numberOfInsertedCategories,
                                whileCounterToCalculateMatch));

                        currentIterationGamesWithMatch.add(game);
                        gamesAlreadyAdded.add(game);
                    }
                }
                if (currentIterationGamesWithMatch.size() > 1) {
                    gamesWithMatch.addAll(calculateGameMatchByRating(
                            currentIterationGamesWithMatch,
                            numberOfInsertedCategories,
                            whileCounterToCalculateMatch));
                }
                else if (currentIterationGamesWithMatch.size() == 1){
                    gamesWithMatch.addAll(currentIterationGamesWithMatch);
                }
            }
            categoryNames.remove(categoryNames.size()-1);
            whileCounterToCalculateMatch++;
        }

        return gamesWithMatch
                .stream()
                .sorted((o1, o2) -> o2.getGameMatch().compareTo(o1.getGameMatch()))
                .collect(Collectors.toList());
    }

    /**
     * Created by Piotr Romanczak on 11-11-2021
     * Description: this method calculates gameMatch of current while loop
     * @param numberOfInsertedCategories
     * @param whileCounterToCalculateMatch
     * @return Double
     */
    private Double calculateCurrentGameMatch(int numberOfInsertedCategories, int whileCounterToCalculateMatch) {
        Double currentGameMatch = ((numberOfInsertedCategories - whileCounterToCalculateMatch)  * 100d / numberOfInsertedCategories);

        return BigDecimal.valueOf(currentGameMatch)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Created by Piotr Romanczak on 11-11-2021
     * Description: this method calculates gameMatch of current while loop
     * when there are more than one game with the same match
     * @param currentGroupMatchValue
     * @param matchValueRange
     * @param mapIterator
     * @param mapSize
     * @return Double
     */
    private Double calculateNewGameMatch (Double currentGroupMatchValue, Double matchValueRange, int mapIterator, int mapSize) {
        Double newGameMatch = currentGroupMatchValue - (matchValueRange / mapSize) * mapIterator;

        return BigDecimal.valueOf(newGameMatch)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }


    /**
     * Created by Piotr Romanczak on 11-11-2021
     * Description: this method calculates is used to recalculate gameMatch field value
     * when there is more than one game with the same match
     * but only if match is higher than 50
     * @param gamesWithMatchToRecalculate
     * @param numberOfCategories
     * @param numberOfIterations
     * @return List <Game>
     */
    private List <Game> calculateGameMatchByRating(List <Game> gamesWithMatchToRecalculate, int numberOfCategories, int numberOfIterations) {
        List <Game> gamesWithNewMatchValues = new ArrayList<>();
        Double currentGroupMatchValue = gamesWithMatchToRecalculate.get(0).getGameMatch();

        if (currentGroupMatchValue >= 50) {
            Double nextGroupMatchValue = ((numberOfCategories - numberOfIterations - 1) * 100d / numberOfCategories);
            Double matchValueRange = currentGroupMatchValue - nextGroupMatchValue;
            Map <Game, Double> gamesValuedByRatings = new HashMap<>();

            for (Game gameByMatch : gamesWithMatchToRecalculate) {
                gamesValuedByRatings.put(gameByMatch, gameByMatch.getRating() * gameByMatch.getNumberOfVotes());
            }
            Map <Game, Double> gamesValuedByRatingsSorted = Utils.sortByValue(gamesValuedByRatings);

            int mapIterator = 0;
            for (Map.Entry <Game, Double> gameByRating : gamesValuedByRatingsSorted.entrySet()) {
                gameByRating.getKey().setGameMatch(calculateNewGameMatch(currentGroupMatchValue, matchValueRange, mapIterator, gamesWithMatchToRecalculate.size()));
                gamesWithNewMatchValues.add(gameByRating.getKey());
                mapIterator++;
            }
            return gamesWithNewMatchValues;
        }
        return gamesWithMatchToRecalculate;
    }
}
