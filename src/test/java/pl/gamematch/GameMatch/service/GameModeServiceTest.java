package pl.gamematch.GameMatch.service;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gamematch.GameMatch.TestDataFactory.GameModeDataFactory;
import pl.gamematch.GameMatch.dao.GameModeRepository;
import pl.gamematch.GameMatch.model.game.GameMode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameModeServiceTest {

    private static final int NUMBER_OF_ELEMENTS = 3;

    @Mock
    private GameModeRepository gameModeRepositoryMock;

    @InjectMocks
    private GameModeService gameModeService;

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();

    @Test
    void shouldReturnAllGameModes() {
        //given
        when(gameModeRepositoryMock.findAll())
                .thenReturn(GameModeDataFactory.createGameModeList(NUMBER_OF_ELEMENTS));
        //when
        List<GameMode> gameModeList = gameModeService.getAllGameModes();
        //then
        assertEquals(NUMBER_OF_ELEMENTS, gameModeList.size());
    }
}