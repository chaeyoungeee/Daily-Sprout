package ds.coremodule.achievement.domain;

import ds.commonmodule.domain.BaseEntity;
import ds.coremodule.habit.domain.Habit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Achievement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AchievementStatus status = AchievementStatus.NOT_ACHIEVED;

    @Column(nullable = false, updatable = false)
    private LocalDate habitDate;
}
