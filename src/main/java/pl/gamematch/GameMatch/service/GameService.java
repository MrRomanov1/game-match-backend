package pl.gamematch.GameMatch.service;

import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.GameCategoryRepository;
import pl.gamematch.GameMatch.dao.GameRepository;
import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.model.game.GameMode;
import pl.gamematch.GameMatch.model.game.Platform;
import pl.gamematch.GameMatch.utils.GameCategoryUtils;
import pl.gamematch.GameMatch.utils.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameRepository gameRepository;
    private GameCategoryRepository gameCategoryRepository;

    public GameService(GameRepository gameRepository, GameCategoryRepository gameCategoryRepository) {
        this.gameRepository = gameRepository;
        this.gameCategoryRepository = gameCategoryRepository;
    }

    /**
     * Created by Piotr Romanczak on 20-11-2021
     * Description: this method returns a Game object by provided Id
     * @param id
     * @return Game
     */
    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    /**
     * Created by Piotr Romanczak on 20-11-2021
     * Description: this method returns List of games by provided category name
     * @param name
     * @return List<Game>
     */
    public List<Game> getGamesByCategory(String name) {
        return gameRepository.findGamesByGameCategoriesName(name);
    }

    /**
     * Created by Piotr Romanczak on 20-11-2021
     * Description: this method returns List of all games
     * @return List<Game>
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Created by Piotr Romanczak on 21-11-2021
     * Description: this method returns List of all games by provided GameCategory List
     * @param inGameCategories
     * @return List<Game>
     */
    public List<Game> handleGameMatch(ArrayList<GameCategory> inGameCategories, ArrayList<GameMode> inGameModes, ArrayList<Platform> inPlatforms) {

        List<GameCategory> gameCategories =
                gameCategoryRepository
                        .findGameCategoriesByNameIn(getGameCategoryNames(inGameCategories));

        List<GameCategory> categoriesSortedByRatings = getGameCategoriesSortedByRating(gameCategories);

        List<Game> gameList = gameRepository.findGamesByGameCategoriesIn(categoriesSortedByRatings);
        List<Game> gameListByGameModes = gameRepository.findGamesByGameModesIn(inGameModes);
        List<Game> gameListByPlatforms = gameRepository.findGamesByPlatformsIn(inPlatforms);
        Set<Game> gameListWithoutDuplicates = Set.copyOf(gameList);

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
     * Created by Piotr Romanczak on 21-11-2021
     * Description: this method returns List of gameCategories sorted by their rating
     * @param gameCategories
     * @return List<GameCategory>
     */
    private List<GameCategory> getGameCategoriesSortedByRating(List<GameCategory> gameCategories) {
        Map<GameCategory, Double> gameCategoryByRating = new HashMap<>();

        for (GameCategory category : gameCategories) {
            gameCategoryByRating.put(category, category.calculateCategoryRating());
        }

        return GameCategoryUtils.sortCategoriesByRatings(gameCategoryByRating);
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
     * Description: this method calculates is used to recalculate gameMatch field value
     * when there is more than one game with the same match
     * but only if match is higher than 50
     * @param gamesWithMatchToRecalculate
     * @param numberOfCategories
     * @param numberOfIterations
     * @return List <Game>
     */
    private List <Game> calculateGameMatchByRating(List <Game> gamesWithMatchToRecalculate, int numberOfCategories, int numberOfIterations) {
        Double currentGroupMatchValue = gamesWithMatchToRecalculate.get(0).getGameMatch();

        if (currentGroupMatchValue >= 50) {
            return getCurrentGroupMatch(gamesWithMatchToRecalculate, numberOfCategories, numberOfIterations, currentGroupMatchValue);
        }
        return gamesWithMatchToRecalculate;
    }

    /**
     * Created by Piotr Romanczak on 21-11-2021
     * Description: this method returns List of gameCategory names from provided category list
     * @param inGameCategories
     * @return List<String>
     */
    private List<String> getGameCategoryNames(ArrayList<GameCategory> inGameCategories) {
        List<String> gameCategoryNames = new ArrayList<>();
        for (GameCategory gameCategoryFromInput : inGameCategories) {
            gameCategoryNames.add(gameCategoryFromInput.getName());
        }
        return gameCategoryNames;
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
     * Description: this method gameMatch of current group iteration
     * when there are more than one game with the same match
     * @param gamesWithMatchToRecalculate
     * @param numberOfCategories
     * @param numberOfIterations
     * @param currentGroupMatchValue
     * @return List <Game>
     */
    private List <Game> getCurrentGroupMatch(List <Game> gamesWithMatchToRecalculate, int numberOfCategories, int numberOfIterations, Double currentGroupMatchValue ) {
        List <Game> gamesWithNewMatchValues = new ArrayList<>();
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
}