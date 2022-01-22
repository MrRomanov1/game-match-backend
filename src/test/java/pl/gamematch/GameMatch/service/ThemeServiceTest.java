package pl.gamematch.GameMatch.service;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gamematch.GameMatch.TestDataFactory.ThemeDataFactory;
import pl.gamematch.GameMatch.dao.ThemeRepository;
import pl.gamematch.GameMatch.model.game.Theme;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    private static final int NUMBER_OF_ELEMENTS = 3;

    @Mock
    private ThemeRepository themeRepositoryMock;

    @InjectMocks
    private ThemeService themeService;

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();

    @Test
    void shouldReturnAllPlatforms() {
        //given
        when(themeRepositoryMock.findAll())
                .thenReturn(ThemeDataFactory.createThemeList(NUMBER_OF_ELEMENTS));
        //when
        List<Theme> themeList = themeService.getAllThemes();
        //then
        assertEquals(NUMBER_OF_ELEMENTS, themeList.size());
    }
}