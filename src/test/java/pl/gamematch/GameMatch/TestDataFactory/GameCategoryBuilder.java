package pl.gamematch.GameMatch.TestDataFactory;

import pl.gamematch.GameMatch.model.game.GameCategory;

public class GameCategoryBuilder {

    private String name;
    private String alias;
    private Double rating;
    private Long numberOfVotes;

    public GameCategoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public GameCategoryBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public GameCategoryBuilder rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public GameCategoryBuilder numberOfVotes(Long numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
        return this;
    }

    public GameCategory build() {
        return new GameCategory(name, alias, rating, numberOfVotes);
    }
}
