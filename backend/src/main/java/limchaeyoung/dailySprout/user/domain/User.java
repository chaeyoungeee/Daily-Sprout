package limchaeyoung.dailySprout.user.domain;

import jakarta.persistence.*;
import limchaeyoung.dailySprout.common.domain.BaseEntity;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
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