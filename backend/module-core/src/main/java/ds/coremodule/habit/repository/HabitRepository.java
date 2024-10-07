package ds.coremodule.habit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import ds.coremodule.habit.domain.Habit;
import ds.coremodule.habit.domain.HabitStatus;
import ds.coremodule.user.domain.User;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findAllByUserAndStatus(User user, HabitStatus status);

    Optional<Habit> findByHabitIdAndUser(Long habitId, User user);
}
