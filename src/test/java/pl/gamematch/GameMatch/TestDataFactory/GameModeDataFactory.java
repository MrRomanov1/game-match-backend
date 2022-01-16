package pl.gamematch.GameMatch.TestDataFactory;

import com.github.javafaker.Faker;
import pl.gamematch.GameMatch.model.game.GameMode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameModeDataFactory {

    private static final Faker faker = new Faker();

    private GameModeDataFactory() {}

    public static GameMode createGameMode() {
        return new GameModeBuilder()
                .name(faker.esports().league())
                .build();
    }

    public static List<GameMode> createGameModeList (int numberOfElements) {
        return IntStream.range(0, numberOfElements)
                .mapToObj(i -> createGameMode())
                .collect(Collectors.toList());
    }
}
