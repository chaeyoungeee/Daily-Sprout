package limchaeyoung.dailySprout.habit.domain;

import jakarta.persistence.*;
import limchaeyoung.dailySprout.common.domain.BaseEntity;
import limchaeyoung.dailySprout.user.domain.User;
import lombok.Getter;

@Entity
@Getter
public class Habit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HabitStatus status;

    @Column(nullable = false)
    private String content;
}
