package pl.gamematch.GameMatch.service;

import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.GameCategoryRepository;
import pl.gamematch.GameMatch.model.game.GameCategory;
import pl.gamematch.GameMatch.utils.GameCategoryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameCategoryService {

    private GameCategoryRepository gameCategoryRepository;

    public GameCategoryService(GameCategoryRepository gameCategoryRepository) {
        this.gameCategoryRepository = gameCategoryRepository;
    }

    /**
     * Created by Piotr Romanczak on 21-11-2021
     * Description: this method returns all gameCategories sorted by ratings
     * @return List<GameCategory>
     */
    public List<GameCategory> getAllGameCategoriesSortedByRatings() {
        List<GameCategory> unorderedGameCategoryList = gameCategoryRepository.findAll();

        Map<GameCategory, Double> gameCategoryByRating = new HashMap<>();

        for (GameCategory category : unorderedGameCategoryList) {
            gameCategoryByRating.put(category, category.calculateCategoryRating());
        }

        return GameCategoryUtils.sortCategoriesByRatings(gameCategoryByRating);
    }
}
