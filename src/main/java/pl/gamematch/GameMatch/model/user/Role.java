package pl.gamematch.GameMatch.model.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Piotr Romanczak on 23-09-2021
 * Description: Role class
 */

//@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRole roleName;
}
