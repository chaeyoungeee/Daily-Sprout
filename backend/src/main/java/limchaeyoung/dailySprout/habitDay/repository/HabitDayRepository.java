package limchaeyoung.dailySprout.habitDay.repository;

import limchaeyoung.dailySprout.habitDay.domain.HabitDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitDayRepository extends JpaRepository<HabitDay, Long> {
}
