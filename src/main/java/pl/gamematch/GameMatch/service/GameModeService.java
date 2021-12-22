package pl.gamematch.GameMatch.service;

import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.GameModeRepository;
import pl.gamematch.GameMatch.model.game.GameMode;

import java.util.List;

@Service
public class GameModeService {

    private GameModeRepository gameModeRepository;

    public GameModeService(GameModeRepository gameModeRepository) {
        this.gameModeRepository = gameModeRepository;
    }

    /**
     * Created by Piotr Romanczak on 22-12-2021
     * Description: this method returns all GameModes
     * @return List<GameMode>
     */
    public List<GameMode> getAllGameModes() {
        return gameModeRepository.findAll();
    }

    /**
     * Created by Piotr Romanczak on 22-12-2021
     * Description: this method inserts GameModes to database
     * @param gameModes
     */
    public void insertGameModes (List<GameMode> gameModes) {
        gameModeRepository.saveAll(gameModes);
    }
}
