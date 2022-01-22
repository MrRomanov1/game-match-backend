package pl.gamematch.GameMatch.service;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import pl.gamematch.GameMatch.TestDataFactory.PlatformDataFactory;
import pl.gamematch.GameMatch.dao.PlatformRepository;
import pl.gamematch.GameMatch.model.game.Platform;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlatformServiceTest {

    private static final int NUMBER_OF_ELEMENTS = 3;

    @Mock
    private PlatformRepository platformRepositoryMock;

    @InjectMocks
    private PlatformService platformService;

    @Rule
    private final MockitoRule rule = MockitoJUnit.rule();

    @Test
    void shouldReturnAllPlatforms() {
        //given
        when(platformRepositoryMock.findAll(Sort.by(Sort.Direction.ASC, "order")))
                .thenReturn(PlatformDataFactory.createPlatformList(NUMBER_OF_ELEMENTS));
        //when
        List<Platform> platformList = platformService.getAllPlatforms();
        //then
        assertEquals(NUMBER_OF_ELEMENTS, platformList.size());
    }
}