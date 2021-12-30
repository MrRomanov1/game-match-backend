package pl.gamematch.GameMatch.service;

import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.*;
import pl.gamematch.GameMatch.model.game.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameRepository gameRepository;
    private GameCategoryRepository gameCategoryRepository;
    private GameModeRepository gameModeRepository;
    private PlatformRepository platformRepository;
    private ThemeRepository themeRepository;

    public GameService(
            GameRepository gameRepository,
            GameCategoryRepository gameCategoryRepository,
            GameModeRepository gameModeRepository,
            PlatformRepository platformRepository,
            ThemeRepository themeRepository) {
        this.gameRepository = gameRepository;
        this.gameCategoryRepository = gameCategoryRepository;
        this.gameModeRepository = gameModeRepository;
        this.platformRepository = platformRepository;
        this.themeRepository = themeRepository;
    }

    /**
     * Created by Piotr Romanczak on 20-11-2021
     * Description: this method returns a Game object by provided alias
     *
     * @param alias
     * @return Game
     */
    public Game getGameByAlias(String alias) {
        return gameRepository.findByAlias(alias);
    }

    /**
     * Created by Piotr Romanczak on 20-11-2021
     * Description: this method returns List of games by provided category name
     *
     * @param name
     * @return List<Game>
     */
    public Set<Game> getGamesByCategory(String name) {
        if (!gameCategoryRepository.findGameCategoriesByAlias(name).isEmpty()) {
            return Set.copyOf(gameRepository.findGamesByGameCategoriesAlias(name));
        }
        if (!gameModeRepository.findGameModesByName(name).isEmpty()) {
            return Set.copyOf(gameRepository.findGamesByGameModesName(name));
        }
        if (!platformRepository.findPlatformsByType(name).isEmpty()) {
            return Set.copyOf(gameRepository.findGamesByPlatformsType(name));
        }
        if (!themeRepository.findThemesByAlias(name).isEmpty()) {
            return Set.copyOf(gameRepository.findGamesByThemesAlias(name));
        }
        return Collections.emptySet();
    }

    /**
     * Created by Piotr Romanczak on 20-11-2021
     * Description: this method returns List of all games
     *
     * @return List<Game>
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Created by Piotr Romanczak on 21-11-2021
     * Description: this method returns List of all games by provided GameCategory List
     *
     * @param inGameWrapper
     * @return List<Game>
     */
    public List<Game> handleGameMatch(GameWrapper inGameWrapper) {
        ArrayList<GameCategory> inGameCategories = inGameWrapper.getGameCategories();
        ArrayList<Theme> inThemes = inGameWrapper.getThemes();
        List<GameMode> inGameModes = inGameWrapper.getGameModes();
        List<Platform> inPlatforms = inGameWrapper.getPlatforms();

        List<GameCategory> gameCategories =
                gameCategoryRepository
                        .findGameCategoriesByNameIn(getGameCategoryNames(inGameCategories));

        List<Theme> themes = themeRepository.findThemesByNameIn(getThemeNames(inThemes));

        List<Game> gameListByCategories = gameRepository.findGamesByGameCategoriesIn(gameCategories);
        List<Game> gameListByGameModes = gameRepository.findGamesByGameModesIn(inGameModes);
        List<Game> gameListByPlatforms = gameRepository.findGamesByPlatformsIn(inPlatforms);
        List<Game> gameListByThemes = gameRepository.findGamesByThemesIn(inThemes);

        Set<Game> gameListByOtherConditions = getGamesByOtherConditions(gameListByGameModes, gameListByPlatforms);

        if (inGameCategories.isEmpty() && inThemes.isEmpty()) {
            if (gameListByOtherConditions.isEmpty()) {
                return Collections.emptyList();
            } else {
                return gameListByOtherConditions.stream()
                        .sorted(Comparator.comparingDouble(Game::calculateGameRating))
                        .collect(Collectors.toList());
            }
        }
        Set<Game> gameListWithoutDuplicates = getGamesByAllConditions(gameListByCategories, gameListByOtherConditions, gameListByThemes);

        return handleGameMatchingCalculations(gameListWithoutDuplicates, gameCategories, themes);
    }

    /**
     * Created by Piotr Romanczak on 10-11-2021
     * Description: this method calculates gameMatch field of all games
     *
     * @param gameList
     * @param categories
     * @param themes
     * @return List<Game>
     */
    private List<Game> handleGameMatchingCalculations(Set<Game> gameList, List<GameCategory> categories, List<Theme> themes) {

        double parameterFactor = (!categories.isEmpty() && !themes.isEmpty() ? 0.5d : 1d);

        if (categories.size() > 0) {
            calculateMatchByCategory(gameList, categories, parameterFactor);
        }

        if (themes.size() > 0) {
            calculateMatchByTheme(gameList, themes, parameterFactor);
        }
        if (!gameList.isEmpty()) {
            handleNullGameMatch(gameList);

            return gameList
                    .stream()
                    .sorted((o1, o2) -> o2.getGameMatch().compareTo(o1.getGameMatch()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method calculates gameMatch of current games by category
     * @param gameList
     * @param categories
     * @param parameterFactor
     * @return Set<Game>
     */
    private Set<Game> calculateMatchByCategory (Set<Game> gameList, List<GameCategory> categories, double parameterFactor) {
        for (GameCategory category : categories) {
            for (Game game : gameList) {
                if (game.getSingleGameCategoriesNames().contains(category.getName())) {
                    game.setGameMatch(calculateNewGameMatch(game, categories.size(), parameterFactor));
                }
            }
        }
        return gameList;
    }

    /**
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method calculates gameMatch of current games by theme
     * @param gameList
     * @param themes
     * @param parameterFactor
     * @return Set<Game>
     */
    private Set<Game> calculateMatchByTheme (Set<Game> gameList, List<Theme> themes, double parameterFactor) {
        for (Theme theme : themes) {
            for (Game game : gameList) {
                if (game.getSingleThemeNames().contains(theme.getName())) {
                    game.setGameMatch(calculateNewGameMatch(game, themes.size(), parameterFactor));
                }
            }
        }
        return gameList;
    }

    /**
     * Created by Piotr Romanczak on 21-11-2021
     * Description: this method returns List of gameCategory names from provided category list
     *
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
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method returns List of gameCategory names from provided category list
     *
     * @param inThemes
     * @return List<String>
     */
    private List<String> getThemeNames(ArrayList<Theme> inThemes) {
        List<String> themeNames = new ArrayList<>();
        for (Theme themeFromInput : inThemes) {
            themeNames.add(themeFromInput.getName());
        }
        return themeNames;
    }

    /**
     * Created by Piotr Romanczak on 11-11-2021
     * Description: this method calculates gameMatch of current while loop
     * when there are more than one game with the same match
     *
     * @param game
     * @param numberOfParameters
     * @param parameterFactor
     * @return Double
     */
    private Double calculateNewGameMatch(Game game, int numberOfParameters, double parameterFactor) {
        Double gameMatchToAdd = (100d / numberOfParameters) * parameterFactor;
        double newGameMatch = 0d;
        if (game.getGameMatch() == null) {
            newGameMatch += gameMatchToAdd;
        } else {
            newGameMatch = game.getGameMatch() + gameMatchToAdd;
        }

        return BigDecimal.valueOf(newGameMatch)
                .setScale(0, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Created by Piotr Romanczak on 06-12-2021
     * Description: this method intersects list of games by other conditions
     *
     * @param gameListByGameModes
     * @param gameListByPlatforms
     * @return Set <Game>
     */
    private Set<Game> getGamesByOtherConditions(List<Game> gameListByGameModes, List<Game> gameListByPlatforms) {
        Set<Game> gamesByOtherConditions = new HashSet<>();
        if (!gameListByGameModes.isEmpty() && !gameListByPlatforms.isEmpty()) {
            for (Game game : gameListByGameModes) {
                if (gameListByPlatforms.contains(game)) {
                    gamesByOtherConditions.add(game);
                }
            }
        } else if (!gameListByGameModes.isEmpty()) {
            return Set.copyOf(gameListByGameModes);
        } else if (!gameListByPlatforms.isEmpty()) {
            return Set.copyOf(gameListByPlatforms);
        }
        return gamesByOtherConditions;
    }

    /**
     * Created by Piotr Romanczak on 06-12-2021
     * Description: this method intersects list of games with list of games by other conditions
     *
     * @param gameListByCategories
     * @param gameListByOtherConditions
     * @param gameListByThemes
     * @return Set <Game>
     */
    private Set<Game> getGamesByAllConditions(List<Game> gameListByCategories, Set<Game> gameListByOtherConditions, List<Game> gameListByThemes) {
        Set<Game> gameListByAllConditions = new HashSet<>();

        if (gameListByCategories.isEmpty() && gameListByThemes.isEmpty() && !gameListByOtherConditions.isEmpty()) {
            return gameListByOtherConditions;
        }
        else if (!gameListByOtherConditions.isEmpty()) {
            if (!gameListByCategories.isEmpty()) {
                for (Game game : gameListByCategories) {
                    if (gameListByOtherConditions.contains(game)) {
                        gameListByAllConditions.add(game);
                    }
                }
            }
            if (!gameListByThemes.isEmpty()) {
                for (Game game : gameListByThemes) {
                    if (gameListByOtherConditions.contains(game)) {
                        gameListByAllConditions.add(game);
                    }
                }
            }
            return gameListByAllConditions;
        }
        else if (gameListByOtherConditions.isEmpty()) {
            if (!gameListByCategories.isEmpty()) {
                gameListByAllConditions.addAll(gameListByCategories);
            }
            if (!gameListByThemes.isEmpty()) {
                gameListByAllConditions.addAll(gameListByThemes);
            }
            return gameListByAllConditions;
        }
        return null;
    }

    /**
     * Created by Piotr Romanczak on 31-12-2021
     * Description: this method sets null gameMatch to 0
     * @param games
     */
    private void handleNullGameMatch(Set<Game> games) {
        for (Game game : games) {
            if (game.getGameMatch() == null) {
                game.setGameMatch(0d);
            }
        }
    }

    /**
     * Created by Piotr Romanczak on 18-12-2021
     * Description: this method returns List of all games that have not been released yet
     *
     * @return List<Game>
     */
    public List<Game> getNotReleasedGames() {
        List<Game> notReleasedGames = new ArrayList<>();

        Date today = new Date();
        List<Game> allGames = gameRepository.findAll();

        for (Game game : allGames) {
            if (game.getReleaseDate().after(today)) {
                notReleasedGames.add(game);
            }
        }
        return notReleasedGames;
    }

    /**
     * Created by Piotr Romanczak on 18-12-2021
     * Description: this method returns List of most popular games
     *
     * @return List<Game>
     */
    public List<Game> getPopularGames() {
        List<Game> allGames = gameRepository.findAll();

        return allGames.stream()
                .sorted(Comparator.comparingDouble(Game::calculateGameRating).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Created by Piotr Romanczak on 18-12-2021
     * Description: this method returns List of most popular games
     *
     * @return List<Game>
     */
    public List<Game> getHighRatedGames() {
        List<Game> allGames = gameRepository.findAll();

        return allGames.stream()
                .sorted(Comparator.comparingDouble(Game::getRating).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Created by Piotr Romanczak on 17-12-2021
     * Description: this method inserts Games to database
     *
     * @param games
     */
    public void insertGame(List<Game> games) {
        gameRepository.saveAll(games);
    }
}