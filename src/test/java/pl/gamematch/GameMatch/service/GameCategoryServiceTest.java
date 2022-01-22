package pl.gamematch.GameMatch.service;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gamematch.GameMatch.TestDataFactory.GameCategoryDataFactory;
import pl.gamematch.GameMatch.dao.GameCategoryRepository;
import pl.gamematch.GameMatch.model.game.GameCategory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameCategoryServiceTest {

    private static final int NUMBER_OF_ELEMENTS = 3;

    @Mock
    private GameCategoryRepository gameCategoryRepositoryMock;

    @InjectMocks
    private GameCategoryService gameCategoryService;

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();

    @Test
    void shouldReturnSortedGameCategoryList() {
        //given
        when(gameCategoryRepositoryMock.findAll())
                .thenReturn(GameCategoryDataFactory.createGameCategoryList(NUMBER_OF_ELEMENTS));
        //when
        List<GameCategory> gameCategories = new ArrayList<>(gameCategoryService.getAllGameCategoriesSortedByRatings());
        //then
        assert (gameCategories.size() == NUMBER_OF_ELEMENTS);
        assert (gameCategories.get(0).calculateCategoryRating() > gameCategories.get(1).calculateCategoryRating());
        assert (gameCategories.get(1).calculateCategoryRating() > gameCategories.get(2).calculateCategoryRating());
    }

    @Test
    void shouldInsertGameCategories() {
        //given
        List<GameCategory> gameCategories = GameCategoryDataFactory.createGameCategoryList(NUMBER_OF_ELEMENTS);
        //when
        when(gameCategoryRepositoryMock.saveAll(anyList())).thenReturn(gameCategories);
        gameCategoryService.insertGameCategories(gameCategories);
        //then
        verify(gameCategoryRepositoryMock, times(1)).saveAll(anyList());
    }
}