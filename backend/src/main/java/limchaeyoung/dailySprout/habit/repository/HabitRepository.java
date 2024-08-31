package limchaeyoung.dailySprout.habit.repository;

import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.domain.HabitStatus;
import limchaeyoung.dailySprout.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findAllByUserAndStatus(User user, HabitStatus status);

    Optional<Habit> findByHabitIdAndUser(Long habitId, User user);
}
