package pl.gamematch.GameMatch.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.PlatformRepository;
import pl.gamematch.GameMatch.model.game.Platform;

import java.util.List;

@Service
public class PlatformService{

    private PlatformRepository platformRepository;

    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    /**
     * Created by Piotr Romanczak on 22-12-2021
     * Description: this method returns all Platforms
     * @return List<Platform>
     */
    public List<Platform> getAllPlatforms() {
        return platformRepository.findAll(Sort.by(Sort.Direction.ASC, "order"));
    }

    /**
     * Created by Piotr Romanczak on 22-12-2021
     * Description: this method inserts Platforms to database
     * @param platforms
     */
    public void insertPlatforms (List<Platform> platforms) {
        platformRepository.saveAll(platforms);
    }
}
