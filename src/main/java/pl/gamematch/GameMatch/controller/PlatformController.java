package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.model.game.Platform;
import pl.gamematch.GameMatch.service.PlatformService;

import java.util.List;

/**
 * Created by Piotr Romanczak on 05-12-2021
 * Description: PlatformController class
 */

@RestController
public class PlatformController {

    private PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    /**
     * Created by Piotr Romanczak on 05-12-2021
     * Description: this method returns List of all gameModes
     * @return List<GameMode>
     */
    @GetMapping("/platforms")
    public List<Platform> getAllPlatforms() {
        return platformService.getAllPlatforms();
    }

    /**
     * Created by Piotr Romanczak on 22-12-2021
     * Description: this method inserts Platforms
     * @param platforms
     */
    @PostMapping("/platforms")
    public void insertPlatforms (@RequestBody List<Platform> platforms) {
        platformService.insertPlatforms(platforms);
    }
}
