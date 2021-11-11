package pl.gamematch.GameMatch.utils;

import pl.gamematch.GameMatch.model.game.GameCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Piotr Romanczak on 11-11-2021
 * Description: GameCategoryUtils utility class
 */
public class GameCategoryUtils {

    /**
     * Created by Piotr Romanczak on 11-11-2021
     * Description: this method sorts game categories by ratings descending
     * @param unorderedCategories
     * @return List<GameCategory>
     */
    public static List<GameCategory> sortCategoriesByRatings(Map<GameCategory, Double> unorderedCategories) {
        return unorderedCategories
                .entrySet().stream().sorted(Comparator
                        .comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
