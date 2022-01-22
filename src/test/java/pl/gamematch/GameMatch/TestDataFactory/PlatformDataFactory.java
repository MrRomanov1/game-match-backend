package pl.gamematch.GameMatch.TestDataFactory;

import com.github.javafaker.Faker;
import pl.gamematch.GameMatch.model.game.Platform;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlatformDataFactory {

    private static final Faker faker = new Faker();

    private PlatformDataFactory() {}

    public static Platform createPlatform() {
        return new PlatformBuilder()
                .name(faker.food().ingredient())
                .type(faker.food().ingredient())
                .build();
    }

    public static List<Platform> createPlatformList (int numberOfElements) {
        return IntStream.range(0, numberOfElements)
                .mapToObj(i -> createPlatform())
                .collect(Collectors.toList());
    }
}
