package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.service.GameCategoryService;

import java.util.List;

/**
 * Created by Piotr Romanczak on 12-11-2021
 * Description: GameCategoryController class
 */
@RestController
public class GameCategoryController {

    private GameCategoryService gameCategoryService;

    public GameCategoryController(GameCategoryService gameCategoryService) {
        this.gameCategoryService = gameCategoryService;
    }

    /**
     * Created by Piotr Romanczak on 12-11-2021
     * Description: this method returns List of all gameCategories
     * @return List<Game>
     */
    @GetMapping("/game-categories")
    public List<GameCategory> getAllGameCategoriesSortedByRatings() {
        return gameCategoryService.getAllGameCategoriesSortedByRatings();
    }
}
