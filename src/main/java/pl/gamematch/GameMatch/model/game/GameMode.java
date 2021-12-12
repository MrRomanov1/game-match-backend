package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Piotr Romanczak on 05-12-2021
 * Description: GameMode entity
 */

@Entity
@Table(name="game_mode")
@Getter
@Setter
public class GameMode {

    @Id
    @Column(name = "game_mode_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_mode_name")
    private String name;
}
