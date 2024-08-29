package limchaeyoung.dailySprout.user.domain;

import jakarta.persistence.*;
import limchaeyoung.dailySprout.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    public static User createUser(String email, String nickname) {
        User user = new User();
        user.email = email;
        user.nickname = nickname;
        return user;
    }
}