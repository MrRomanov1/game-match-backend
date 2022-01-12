package pl.gamematch.GameMatch.TestDataFactory;

import com.github.javafaker.Faker;
import pl.gamematch.GameMatch.model.game.GameCategory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameCategoryDataFactory {

    private static final Faker faker = new Faker();
    public static final int MAX_DECIMALS = 2;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 5;


    private GameCategoryDataFactory() {}

    public static GameCategory createGameCategory() {
        return new GameCategoryBuilder()
                .name(faker.esports().game())
                .alias(faker.esports().game())
                .rating(faker.number().randomDouble(MAX_DECIMALS, MIN_NUMBER, MAX_NUMBER))
                .numberOfVotes(faker.random().nextLong()).build();
    }

    public static List<GameCategory> createGameCategoryList (int numberOfElements) {
        return IntStream.range(0, numberOfElements)
                .mapToObj(i -> createGameCategory())
                .collect(Collectors.toList());
    }
}
