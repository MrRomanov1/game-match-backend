package pl.gamematch.GameMatch.TestDataFactory;

import com.github.javafaker.Faker;
import pl.gamematch.GameMatch.model.game.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameDataFactory {

    private static final Faker faker = new Faker();
    public static final int MAX_DECIMALS = 2;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 5;

    private GameDataFactory() {}

    public static Game createGame() {
        return new GameBuilder()
                .title(faker.esports().game())
                .alias(faker.esports().game())
                .rating(faker.random().nextInt(8000))
                .numberOfVotes(faker.random().nextInt(8000))
                .releaseDate(faker.date().future(faker.random().nextInt(8000), TimeUnit.SECONDS))
                .build();
    }

    public static Game createGameWithRelatedRecords(
            List <GameCategory> gameCategories, List <GameMode> gameModes,
            List <Platform> platforms, List <Theme> themes
    ) {
        return new GameBuilder()
                .title(faker.esports().game())
                .alias(faker.esports().game())
                .rating(faker.random().nextInt(8000))
                .numberOfVotes(faker.random().nextInt(8000))
                .gameCategories(gameCategories)
                .gameModes(gameModes)
                .platforms(platforms)
                .themes(themes)
                .build();
    }

    public static List<Game> createGameList (int numberOfElements) {
        return IntStream.range(0, numberOfElements)
                .mapToObj(i -> createGame())
                .collect(Collectors.toList());
    }

    public static List<Game> createGameListWithRelatedRecords (
            int numberOfElements, List <GameCategory> gameCategories,
            List <GameMode> gameModes, List <Platform> platforms,
            List <Theme> themes
    ) {
        return IntStream.range(0, numberOfElements)
                .mapToObj(i -> createGameWithRelatedRecords(
                        gameCategories, gameModes, platforms, themes))
                .collect(Collectors.toList());
    }
}
