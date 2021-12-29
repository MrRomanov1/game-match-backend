package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.model.game.Theme;
import pl.gamematch.GameMatch.service.ThemeService;

import java.util.List;

/**
 * Created by Piotr Romanczak on 29-12-2021
 * Description: ThemeController class
 */

@RestController
public class ThemeController {

    private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    /**
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method returns List of all themes
     * @return List<Theme>
     */
    @GetMapping("/themes")
    public List<Theme> getAllPlatforms() {
        return themeService.getAllThemes();
    }

    /**
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method inserts themes
     * @param themes
     */
    @PostMapping("/themes")
    public void insertGameCategories(@RequestBody List<Theme> themes) {
        themeService.insertThemes(themes);
    }
}
