package pl.gamematch.GameMatch.model.game;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private Long gameCategoryId;

    @Column(name = "game_cat_name")
    private String name;

    @Column(name = "game_cat_rating")
    private Double rating;

    @Column(name = "game_cat_votes")
    private Long numberOfVotes;

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
