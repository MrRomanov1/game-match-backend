package pl.gamematch.GameMatch.TestDataFactory;

import com.github.javafaker.Faker;
import pl.gamematch.GameMatch.model.game.Platform;
import pl.gamematch.GameMatch.model.game.Theme;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThemeDataFactory {
    private static final Faker faker = new Faker();

    private ThemeDataFactory() {}

    public static Theme createTheme() {
        return new ThemeBuilder()
                .name(faker.book().title())
                .alias(faker.book().title())
                .build();
    }

    public static List<Theme> createThemeList (int numberOfElements) {
        return IntStream.range(0, numberOfElements)
                .mapToObj(i -> createTheme())
                .collect(Collectors.toList());
    }
}
