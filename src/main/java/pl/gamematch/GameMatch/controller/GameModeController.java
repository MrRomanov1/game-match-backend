package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.dao.GameModeRepository;
import pl.gamematch.GameMatch.model.game.GameMode;

import java.util.List;

/**
 * Created by Piotr Romanczak on 05-12-2021
 * Description: GameModeController class
 */

@RestController
public class GameModeController {

    private GameModeRepository gameModeRepository;

    public GameModeController(GameModeRepository gameModeRepository) {
        this.gameModeRepository = gameModeRepository;
    }

    /**
     * Created by Piotr Romanczak on 05-12-2021
     * Description: this method returns List of all gameModes
     * @return List<GameMode>
     */
    @GetMapping("/game-modes")
    public List<GameMode> getAllGameModes() {
        return gameModeRepository.findAll();
    }
}
