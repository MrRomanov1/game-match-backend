package pl.gamematch.GameMatch.TestDataFactory;

import pl.gamematch.GameMatch.model.game.GameMode;

public class GameModeBuilder {

    private String name;

    public GameModeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public GameMode build() {
        return new GameMode(name);
    }
}
