package pl.gamematch.GameMatch.service;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gamematch.GameMatch.TestDataFactory.*;
import pl.gamematch.GameMatch.dao.*;
import pl.gamematch.GameMatch.model.game.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private static final int NUMBER_OF_ELEMENTS = 3;
    private static final int NUMBER_OF_CATEGORIES = 2;
    private static final List<Game> gameList = GameDataFactory.createGameList(NUMBER_OF_ELEMENTS);
    private static final List<GameCategory> gameCategories = GameCategoryDataFactory
            .createGameCategoryList(NUMBER_OF_CATEGORIES);
    private static final List<GameMode> gameModes = GameModeDataFactory
            .createGameModeList(NUMBER_OF_CATEGORIES);
    private static final List<Platform> platforms = PlatformDataFactory
            .createPlatformList(NUMBER_OF_CATEGORIES);
    private static final List<Theme> themes = ThemeDataFactory
            .createThemeList(NUMBER_OF_CATEGORIES);
    private static final List<Game> gameListWithRelatedRecords = GameDataFactory
            .createGameListWithRelatedRecords(
                    NUMBER_OF_ELEMENTS, gameCategories,
                    gameModes, platforms, themes
            );

    @Mock
    private GameRepository gameRepositoryMock;
    @Mock
    private GameCategoryRepository gameCategoryRepositoryMock;
    @Mock
    private GameModeRepository gameModeRepositoryMock;
    @Mock
    private PlatformRepository platformRepositoryMock;
    @Mock
    private ThemeRepository themeRepositoryMock;

    @InjectMocks
    private GameService gameService;

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();

    @Test
    void shouldReturnGamesSortedByRating() {
        //given
        //when
        when(gameRepositoryMock.findAll()).thenReturn(gameList);
        List<Game> gamesSortedByRating = gameService.getHighRatedGames();
        //then
        assertEquals(gameList.size(), gamesSortedByRating.size());
        assert (gamesSortedByRating.get(0).getRating() > gamesSortedByRating.get(1).getRating());
        assert (gamesSortedByRating.get(1).getRating() > gamesSortedByRating.get(2).getRating());
    }

    @Test
    void shouldReturnGamesSortedByPopularity() {
        //given
        //when
        when(gameRepositoryMock.findAll()).thenReturn(gameList);
        List<Game> gamesSortedByRating = gameService.getPopularGames();
        //then
        assertEquals(gameList.size(), gamesSortedByRating.size());
        assert (gamesSortedByRating.get(0).calculateGameRating() > gamesSortedByRating.get(1).calculateGameRating());
        assert (gamesSortedByRating.get(1).calculateGameRating() > gamesSortedByRating.get(2).calculateGameRating());
    }

    @Test
    void shouldReturnGamesWithFutureDates() {
        //given
        Date today = new Date();
        //when
        when(gameRepositoryMock.findAll()).thenReturn(gameList);
        List<Game> futureGames = gameService.getNotReleasedGames();
        //then
        assertEquals(gameList.size(), futureGames.size());
        assert (futureGames.get(0).getReleaseDate().after(today));
        assert (futureGames.get(1).getReleaseDate().after(today));
        assert (futureGames.get(2).getReleaseDate().after(today));
    }

    @Test
    void shouldReturnAllGames() {
        //given
        //when
        when(gameRepositoryMock.findAll()).thenReturn(gameList);
        List<Game> allGames = gameService.getAllGames();
        //then
        assertEquals(gameList.size(), allGames.size());
    }

    @Test
    void shouldReturnGameByAlias() {
        //given
        Game game = GameDataFactory.createGame();
        //when
        when(gameRepositoryMock.findByAlias(game.getAlias())).thenReturn(game);
        Game returnedGame = gameService.getGameByAlias(game.getAlias());
        //then
        assertEquals(game.getAlias(), returnedGame.getAlias());
    }

    @Test
    void shouldReturnGamesByCategoryAlias() {
        //given
        //when
        when(gameCategoryRepositoryMock.findGameCategoriesByAlias(anyString()))
                .thenReturn(gameCategories);
        when(gameRepositoryMock.findGamesByGameCategoriesAlias(anyString()))
                .thenReturn(gameListWithRelatedRecords);
        Set<Game> gamesByCategoryAlias = gameService.getGamesByCategory(gameCategories.get(0).getAlias());
        //then
        assertEquals(gameListWithRelatedRecords.size(), gamesByCategoryAlias.size());
        assert (gameListWithRelatedRecords.get(0).getGameCategories().contains(gameCategories.get(0)));
    }

    @Test
    void shouldReturnGamesByModeName() {
        //given
        //when
        when(gameModeRepositoryMock.findGameModesByName(anyString()))
                .thenReturn(gameModes);
        when(gameRepositoryMock.findGamesByGameModesName(anyString()))
                .thenReturn(gameListWithRelatedRecords);
        Set<Game> gamesByModeName = gameService.getGamesByCategory(gameModes.get(0).getName());
        //then
        assertEquals(gameListWithRelatedRecords.size(), gamesByModeName.size());
        assert (gameListWithRelatedRecords.get(0).getGameModes().contains(gameModes.get(0)));
    }

    @Test
    void shouldReturnGamesByPlatform() {
        //given
        //when
        when(platformRepositoryMock.findPlatformsByType(anyString()))
                .thenReturn(platforms);
        when(gameRepositoryMock.findGamesByPlatformsType(anyString()))
                .thenReturn(gameListWithRelatedRecords);
        Set<Game> gamesByPlatform = gameService.getGamesByCategory(platforms.get(0).getType());
        //then
        assertEquals(gameListWithRelatedRecords.size(), gamesByPlatform.size());
        assert (gameListWithRelatedRecords.get(0).getPlatforms().contains(platforms.get(0)));
    }

    @Test
    void shouldReturnGamesByTheme() {
        //given
        //when
        when(themeRepositoryMock.findThemesByAlias(anyString()))
                .thenReturn(themes);
        when(gameRepositoryMock.findGamesByThemesAlias(anyString()))
                .thenReturn(gameListWithRelatedRecords);
        Set<Game> gamesByPlatform = gameService.getGamesByCategory(themes.get(0).getAlias());
        //then
        assertEquals(gameListWithRelatedRecords.size(), gamesByPlatform.size());
        assert (gameListWithRelatedRecords.get(0).getThemes().contains(themes.get(0)));
    }

    @Test
    void shouldReturnEmptyGamesList() {
        //given
        //when
        Set<Game> gamesByConditionName = gameService.getGamesByCategory(anyString());
        //then
        assertEquals(0, gamesByConditionName.size());
    }

    @Test
    void shouldReturnEmptyList() {
        //given
        GameWrapper gameWrapper = new GameWrapper(new ArrayList<>(), Collections.EMPTY_LIST, Collections.EMPTY_LIST, new ArrayList<>());
        //when
        List<Game> matchedGamesList = gameService.handleGameMatch(gameWrapper);
        //then
        assertEquals(0, matchedGamesList.size());
    }

    @Test
    void shouldReturnGamesWithNullMatch() {
        //given
        GameWrapper gameWrapper = new GameWrapper(new ArrayList<>(), gameModes, Collections.EMPTY_LIST, new ArrayList<>());
        //when
        when(gameRepositoryMock.findGamesByGameModesIn(gameModes))
                .thenReturn(gameListWithRelatedRecords);
        List<Game> matchedGamesList = gameService.handleGameMatch(gameWrapper);
        //then
        assertEquals(gameListWithRelatedRecords.size(), matchedGamesList.size());
        for (Game game : matchedGamesList) {
            assertNull(game.getGameMatch());
        }
    }

    @Test
    void shouldReturnGameListWithNotNullGameMatch() {
        //given
        GameWrapper gameWrapper = new GameWrapper(new ArrayList<>(gameCategories), gameModes, Collections.EMPTY_LIST, new ArrayList<>(themes));
        //when
        when(gameCategoryRepositoryMock.findGameCategoriesByNameIn(anyList()))
                .thenReturn(gameCategories);
        when(themeRepositoryMock.findThemesByNameIn(anyList())).thenReturn(themes);
        when(gameRepositoryMock.findGamesByGameCategoriesIn(gameCategories))
                .thenReturn(gameListWithRelatedRecords);
        when(gameRepositoryMock.findGamesByGameModesIn(gameModes))
                .thenReturn(gameListWithRelatedRecords);
        when(gameRepositoryMock.findGamesByThemesIn(themes)).thenReturn(gameListWithRelatedRecords);
        List<Game> matchedGamesList = gameService.handleGameMatch(gameWrapper);
        //then
        assertEquals(gameListWithRelatedRecords.size(), matchedGamesList.size());
        for (Game game : matchedGamesList) {
            assertNotNull(game.getGameMatch());
        }
    }
}