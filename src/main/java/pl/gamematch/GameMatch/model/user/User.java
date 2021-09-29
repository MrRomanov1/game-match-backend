package pl.gamematch.GameMatch.model.user;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Piotr Romanczak on 23-09-2021
 * Description: User class
 */

@Entity
@Table(name="user")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_first_name")
    private String firstName;

    @Column(name = "user_last_name")
    private String lastName;

    /*
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;
    */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<Role> roles;

    /**
     * Created by Piotr Romanczak on 23-09-2021
     * Description: This method is responsible to return full user name
     * @return String fullName
     */

    public String getFullName() {
        return firstName != null ? firstName.concat(" ").concat(lastName) : "";
    }
}
