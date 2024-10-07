package ds.coremodule.habitDay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ds.coremodule.habitDay.domain.HabitDay;

@Repository
public interface HabitDayRepository extends JpaRepository<HabitDay, Long> {
}
