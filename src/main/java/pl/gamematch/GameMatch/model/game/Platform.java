package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Piotr Romanczak on 05-12-2021
 * Description: Platform entity
 */

@Entity
@NoArgsConstructor
@Table(name="platform")
@Getter
@Setter
public class Platform {

    @Id
    @Column(name = "platform_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "platform_name")
    private String name;

    @Column(name = "platform_icon")
    private String icon;

    @Column(name = "platform_order")
    private int order;

    @Column(name = "platform_type")
    private String type;

    public Platform(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
