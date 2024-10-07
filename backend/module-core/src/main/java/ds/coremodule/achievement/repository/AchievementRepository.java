package ds.coremodule.achievement.repository;

import ds.coremodule.achievement.domain.Achievement;
import ds.coremodule.habit.domain.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    @Query("SELECT a FROM Achievement a WHERE a.habit = :habit AND FUNCTION('YEAR', a.habitDate) = :year AND FUNCTION('MONTH', a.habitDate) = :month")
    List<Achievement> findAllByHabitAndHabitDateYearAndHabitDateMonth(@Param("habit") Habit habit, @Param("year") int year, @Param("month") int month);
}
