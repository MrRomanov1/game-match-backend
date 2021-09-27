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
 * Created by Piotr Romanczak on 27-09-2021
 * Description: GameCategory class
 */

@Entity
@Table(name="game_cat")
@Getter
@Setter
public class GameCategory {

    @Id
    @Column(name = "game_cat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_cat_name")
    private String name;

    @Column(name = "game_cat_rating")
    private Double rating;

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
}
