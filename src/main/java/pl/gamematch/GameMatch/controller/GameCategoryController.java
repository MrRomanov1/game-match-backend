package pl.gamematch.GameMatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gamematch.GameMatch.dao.GameCategoryRepository;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.service.GameCategoryService;
import pl.gamematch.GameMatch.utils.GameCategoryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotr Romanczak on 12-11-2021
 * Description: GameCategoryController class
 */
@RestController
public class GameCategoryController {

    private GameCategoryService gameCategoryService;
    private GameCategoryRepository gameCategoryRepository;

    public GameCategoryController(GameCategoryService gameCategoryService, GameCategoryRepository gameCategoryRepository) {
        this.gameCategoryService = gameCategoryService;
        this.gameCategoryRepository = gameCategoryRepository;
    }

    /**
     * Created by Piotr Romanczak on 12-11-2021
     * Description: this method returns List of all games
     * @return List<Game>
     */
    @GetMapping("/game-categories/all")
    public List<GameCategory> getAllGameCategories() {
        List<GameCategory> unorderedGameCategoryList = gameCategoryRepository.findAll();

        Map<GameCategory, Double> gameCategoryByRating = new HashMap<>();

        for (GameCategory category : unorderedGameCategoryList) {
            gameCategoryByRating.put(category, category.calculateCategoryRating());
        }

        return GameCategoryUtils.sortCategoriesByRatings(gameCategoryByRating);
    }
}
