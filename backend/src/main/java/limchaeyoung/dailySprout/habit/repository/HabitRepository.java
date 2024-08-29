package limchaeyoung.dailySprout.habit.repository;

import limchaeyoung.dailySprout.habit.domain.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
}
