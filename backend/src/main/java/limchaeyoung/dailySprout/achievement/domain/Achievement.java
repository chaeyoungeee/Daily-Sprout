package limchaeyoung.dailySprout.achievement.domain;

import jakarta.persistence.*;
import limchaeyoung.dailySprout.common.domain.BaseEntity;
import limchaeyoung.dailySprout.habit.domain.Habit;

import java.time.LocalDate;

@Entity
public class Achievement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDate habitDate;
}
