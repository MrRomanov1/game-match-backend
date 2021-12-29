package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Piotr Romanczak on 23-09-2021
 * Description: Game entity
 */

@Entity
@Table(name="game")
@Getter
@Setter
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "game_title", unique = true)
    private String title;

    @Column(name = "game_alias", unique = true)
    private String alias;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "game_description")
    private String description;

    @Column(name = "game_description_first_header")
    private String descriptionFirstHeader;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "game_description_first")
    private String descriptionFirst;

    @Column(name = "game_image_url")
    private String imageUrl;

    @Column(name = "game_trailer_url")
    private String trailerUrl;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "game_sys_req_low")
    private String systemMinimumRequirements;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "game_sys_req_rec")
    private String systemRecommendedRequirements;

    @Column(name = "game_rating")
    private int rating;

    @Column(name = "game_votes")
    private int numberOfVotes;

    @Column(name = "game_release_date")
    private Date releaseDate;

    private Double gameMatch;

    /*
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private User createdBy;

    @LastModifiedDate
    @Column(name = "modified_by_date")
    private Date modifiedByDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    private Date modifiedBy;
    */

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
            CascadeType.MERGE
    })
    @JoinTable(name = "game_game_cat",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_cat_id")})
    private Collection<GameCategory> gameCategories;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "game_game_mode",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_mode_id")})
    private Collection<GameMode> gameModes;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "game_platform",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "platform_id")})
    private Collection<Platform> platforms;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE
            })
    @JoinTable(name = "game_theme",
            joinColumns = {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(name = "theme_id")})
    private Collection<Theme> themes;

    public List<String> getSingleGameCategoriesNames() {
        List<String> gameCategoryNames = new ArrayList<>();
        for (GameCategory gameCat : gameCategories) {
            gameCategoryNames.add(gameCat.getName());
        }
        return gameCategoryNames;
    }

    public List<String> getSingleThemeNames() {
        List<String> themeNames = new ArrayList<>();
        for (Theme theme : themes) {
            themeNames.add(theme.getName());
        }
        return themeNames;
    }

    public int calculateGameRating() {
        return rating * numberOfVotes;
    }

    public List <String> getPlatformIcons () {
        List <String> platformIcons = new ArrayList<>();
        for (Platform platform : platforms) {
            if (platform.getType().contains("PlayStation") && !platformIcons.contains("fab fa-playstation")) {
                platformIcons.add("fab fa-playstation");
            }
            if (platform.getType().contains("Xbox") && !platformIcons.contains("fab fa-xbox")) {
                platformIcons.add("fab fa-xbox");
            }
            if (platform.getType().contains("PC") && !platformIcons.contains("pi pi-microsoft")) {
                platformIcons.add("pi pi-microsoft");
            }
        }
        return platformIcons;
    }
}
