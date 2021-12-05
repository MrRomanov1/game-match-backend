package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.dao.PlatformRepository;
import pl.gamematch.GameMatch.model.game.Platform;

import java.util.List;

/**
 * Created by Piotr Romanczak on 05-12-2021
 * Description: PlatformController class
 */

@RestController
public class PlatformController {

    private PlatformRepository platformRepository;

    public PlatformController(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    /**
     * Created by Piotr Romanczak on 05-12-2021
     * Description: this method returns List of all gameModes
     * @return List<GameMode>
     */
    @GetMapping("/platforms")
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll();
    }
}
