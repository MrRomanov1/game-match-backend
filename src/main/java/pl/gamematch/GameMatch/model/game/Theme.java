package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Piotr Romanczak on 29-12-2021
 * Description: Theme entity
 */

@Entity
@NoArgsConstructor
@Table(name="theme")
@Getter
@Setter
public class Theme {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "theme_name")
    private String name;

    @Column(name = "theme_alias")
    private String alias;

    public Theme(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
}
