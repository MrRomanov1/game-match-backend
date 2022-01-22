package pl.gamematch.GameMatch.TestDataFactory;

import pl.gamematch.GameMatch.model.game.*;

import java.util.Collection;
import java.util.Date;

public class GameBuilder {

    private String title;
    private String alias;
    private int rating;
    private int numberOfVotes;
    private Date releaseDate;
    private Double gameMatch;
    private Collection<GameCategory> gameCategories;
    private Collection<GameMode> gameModes;
    private Collection<Platform> platforms;
    private Collection<Theme> themes;

    public GameBuilder title(String title) {
        this.title = title;
        return this;
    }

    public GameBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public GameBuilder rating(int rating) {
        this.rating = rating;
        return this;
    }

    public GameBuilder numberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
        return this;
    }

    public GameBuilder releaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public GameBuilder gameMatch(Double gameMatch) {
        this.gameMatch = gameMatch;
        return this;
    }

    public GameBuilder gameCategories(Collection<GameCategory> gameCategories) {
        this.gameCategories = gameCategories;
        return this;
    }

    public GameBuilder gameModes(Collection<GameMode> gameModes) {
        this.gameModes = gameModes;
        return this;
    }

    public GameBuilder platforms(Collection<Platform> platforms) {
        this.platforms = platforms;
        return this;
    }

    public GameBuilder themes(Collection<Theme> themes) {
        this.themes = themes;
        return this;
    }

    public Game build() {
        return new Game(
                title, alias, rating, numberOfVotes,
                releaseDate, gameMatch, gameCategories,
                gameModes, platforms, themes
        );
    }
}
