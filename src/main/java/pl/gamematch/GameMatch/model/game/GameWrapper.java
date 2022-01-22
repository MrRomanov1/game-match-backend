package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GameWrapper {
    private ArrayList<GameCategory> gameCategories;
    private List<GameMode> gameModes;
    private List<Platform> platforms;
    private ArrayList<Theme> themes;

    public GameWrapper(ArrayList<GameCategory> gameCategories, List<GameMode> gameModes, List<Platform> platforms, ArrayList<Theme> themes) {
        this.gameCategories = gameCategories;
        this.gameModes = gameModes;
        this.platforms = platforms;
        this.themes = themes;
    }
}
