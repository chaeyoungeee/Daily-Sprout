package limchaeyoung.dailySprout.achievement.application;

import limchaeyoung.dailySprout.achievement.domain.Achievement;
import limchaeyoung.dailySprout.achievement.repository.AchievementRepository;
import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.domain.HabitStatus;
import limchaeyoung.dailySprout.habit.repository.HabitRepository;
import limchaeyoung.dailySprout.habitDay.domain.DayWeek;
import limchaeyoung.dailySprout.user.domain.User;
import limchaeyoung.dailySprout.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final AchievementRepository achievementRepository;

    public void createAllAchievementInMonth(int year, int month) {

        List<User> allUsers = userRepository.findAll();

        // 모든 유저의 습관 찾아 저장
        allUsers.forEach(user -> {
            // 모든 유저의 습관 ACTIVE한 습관 찾음
            List<Habit> allHabits = habitRepository.findAllByUserAndStatus(user, HabitStatus.ACTIVE);

            allHabits.forEach(habit -> {
                // 주어진 달에서 반복되는 요일에 해당하는 날짜를 찾아 모두 달성 테이블에 저장
                List<LocalDate> all = new ArrayList<>();

                habit.getHabitDays().forEach((habitDay) -> {
                    List<LocalDate> dates = getDatesForDayOfWeekInMonth(year, month, habitDay.getDayWeek());
                    Collections.addAll(all, dates.toArray(new LocalDate[0]));
                });

                List<Achievement> achievements = new ArrayList<>();
                all.forEach(date -> {
                    Achievement achievement = Achievement.builder()
                            .habit(habit)
                            .habitDate(date)
                            .build();
                    achievements.add(achievement);
                });
                achievementRepository.saveAll(achievements);
            });
        });
    }


    // 주어진 달의 주어진 요일에 해당하는 날짜를 모두 찾아 반환하는 함수
    public List<LocalDate> getDatesForDayOfWeekInMonth(int year, int month, DayWeek dayWeek) {
        List<LocalDate> dates = new ArrayList<>();

        // 해당 달의 초일/말일 찾음
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        // 요일에 해당하는 첫번째 날짜 찾음
        java.time.DayOfWeek dayOfWeek = java.time.DayOfWeek.valueOf(dayWeek.toString());
        LocalDate currentDay = firstDayOfMonth.with(dayOfWeek);

        // 첫번째 날짜가 해당 달이 아니라면 일주일 더함
        if (currentDay.isBefore(firstDayOfMonth)) {
            currentDay = currentDay.plusWeeks(1);
        }

        // 해당 달의 요일에 해당하는 모든 날짜 추가함
        while (!currentDay.isAfter(lastDayOfMonth)) {
            dates.add(currentDay);
            currentDay = currentDay.plusWeeks(1);
        }

        return dates;
    }
}
