package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameWrapper {
    private ArrayList<GameCategory> gameCategories;
    private List<GameMode> gameModes;
    private List<Platform> platforms;
}
