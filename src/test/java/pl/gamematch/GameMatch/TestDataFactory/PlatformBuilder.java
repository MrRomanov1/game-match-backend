package pl.gamematch.GameMatch.TestDataFactory;

import pl.gamematch.GameMatch.model.game.Game;
import pl.gamematch.GameMatch.model.game.Platform;

public class PlatformBuilder {

    private String name;
    private String type;

    public PlatformBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlatformBuilder type(String type) {
        this.type = type;
        return this;
    }

    public Platform build() {
        return new Platform(name, type);
    }
}
