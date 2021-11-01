package pl.gamematch.GameMatch.service;

import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.GameCategoryRepository;
import pl.gamematch.GameMatch.dao.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepository;
    private GameCategoryRepository gameCategoryRepository;

    public GameService() {
        this.gameRepository = gameRepository;
        this.gameCategoryRepository = gameCategoryRepository;
    }
}