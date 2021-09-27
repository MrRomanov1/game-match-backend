package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import pl.gamematch.GameMatch.model.user.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Piotr Romanczak on 23-09-2021
 * Description: Game class
 */

@Entity
@Table(name="game")
@Getter
@Setter
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_title")
    private String title;

    @Column(name = "game_description")
    private String description;

    @Column(name = "game_rating")
    private double rating;

    @Column(name = "game_release_date")
    private Date releaseDate;

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


    //todo
    //many-to-many GameCategory
}
