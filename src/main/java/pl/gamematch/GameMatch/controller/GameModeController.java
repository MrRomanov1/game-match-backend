package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.model.game.GameMode;
import pl.gamematch.GameMatch.service.GameModeService;

import java.util.List;

/**
 * Created by Piotr Romanczak on 05-12-2021
 * Description: GameModeController class
 */

@RestController
public class GameModeController {

    private GameModeService gameModeService;

    public GameModeController(GameModeService gameModeService) {
        this.gameModeService = gameModeService;
    }

    /**
     * Created by Piotr Romanczak on 05-12-2021
     * Description: this method returns List of all gameModes
     * @return List<GameMode>
     */
    @GetMapping("/game-modes")
    public List<GameMode> getAllGameModes() {
        return gameModeService.getAllGameModes();
    }

    /**
     * Created by Piotr Romanczak on 22-12-2021
     * Description: this method inserts GameModes
     * @param gameModes
     */
    @PostMapping("/game-modes")
    public void insertGameCategories(@RequestBody List<GameMode> gameModes) {
        gameModeService.insertGameModes(gameModes);
    }
}
