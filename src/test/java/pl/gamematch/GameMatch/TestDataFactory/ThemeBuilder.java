package pl.gamematch.GameMatch.TestDataFactory;

import pl.gamematch.GameMatch.model.game.Theme;

public class ThemeBuilder {

    private String name;
    private String alias;

    public ThemeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ThemeBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public Theme build() {
        return new Theme(name, alias);
    }
}
