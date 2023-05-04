package hu.webvalto.user;


import hu.webvalto.BaseEntity;
import hu.webvalto.database.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column()
    private String name;


    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
