package pl.gamematch.GameMatch.service;

import org.springframework.stereotype.Service;
import pl.gamematch.GameMatch.dao.ThemeRepository;
import pl.gamematch.GameMatch.model.game.Theme;

import java.util.List;

@Service
public class ThemeService {

    private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /**
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method returns all Themes
     * @return List<Theme>
     */
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    /**
     * Created by Piotr Romanczak on 29-12-2021
     * Description: this method inserts Themes to database
     * @param themes
     */
    public void insertThemes (List<Theme> themes) {
        themeRepository.saveAll(themes);
    }
}
