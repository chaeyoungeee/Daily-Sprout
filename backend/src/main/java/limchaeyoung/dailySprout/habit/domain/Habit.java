package limchaeyoung.dailySprout.habit.domain;

import jakarta.persistence.*;
import limchaeyoung.dailySprout.common.domain.BaseEntity;
import limchaeyoung.dailySprout.habitDay.domain.HabitDay;
import limchaeyoung.dailySprout.user.domain.User;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Habit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private HabitStatus status = HabitStatus.ACTIVE;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<HabitDay> habitDays = new HashSet<>();

    public void deactivate() {
        this.status = HabitStatus.INACTIVE;
    }
}
