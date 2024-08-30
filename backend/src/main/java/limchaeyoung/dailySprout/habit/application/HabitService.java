package limchaeyoung.dailySprout.habit.application;

import jakarta.transaction.Transactional;
import limchaeyoung.dailySprout.achievement.domain.Achievement;
import limchaeyoung.dailySprout.achievement.repository.AchievementRepository;
import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.habit.mapper.HabitMapper;
import limchaeyoung.dailySprout.habit.repository.HabitRepository;
import limchaeyoung.dailySprout.habitDay.domain.DayWeek;
import limchaeyoung.dailySprout.habitDay.domain.HabitDay;
import limchaeyoung.dailySprout.habitDay.repository.HabitDayRepository;
import limchaeyoung.dailySprout.user.application.UserService;
import limchaeyoung.dailySprout.user.domain.User;
import limchaeyoung.dailySprout.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitDayRepository habitDayRepository;
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final HabitMapper habitMapper;


    public Habit createHabit(CreateHabitRequest createHabitRequest, String email) {
        User user = userService.findByEmail(email);

        // 습관 저장
        Habit habit = habitMapper.toEntity(createHabitRequest, user);

        // 습관 요일 및 달성 반복 저장
        List<LocalDate> all = new ArrayList<>();

        createHabitRequest.dayWeeks().forEach((day) -> {
            HabitDay habitDay = HabitDay.builder()
                                        .habit(habit)
                                        .dayWeek(day)
                                        .build();
            habit.getHabitDays().add(habitDay);

            List<LocalDate> dates = getDatesForDayOfWeekInMonth(day);
            Collections.addAll(all, dates.toArray(new LocalDate[0]));
        });

        habitRepository.save(habit);

        all.forEach(date -> {
            Achievement achievement = Achievement.builder()
                    .habit(habit)
                    .habitDate(date)
                    .build();
            achievementRepository.save(achievement);
        });

        return habit;
    }

    // 현재 날짜 이후의 현재 달의 주어진 요일에 해당하는 날짜를 모두 찾아 반환하는 함수
    public List<LocalDate> getDatesForDayOfWeekInMonth(DayWeek dayWeek) {
        LocalDate now = LocalDate.now();
        List<LocalDate> dates = new ArrayList<>();

        // 해당 달의 초일/말일 찾음
        YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonthValue());
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        // 요일에 해당하는 첫번째 날짜 찾음
        java.time.DayOfWeek dayOfWeek = java.time.DayOfWeek.valueOf(dayWeek.toString());
        LocalDate currentDay = firstDayOfMonth.with(dayOfWeek);

        // 현재 날짜 이후의 첫번째 날짜가 찾음
        while (currentDay.isBefore(now.minusDays(1))) {
            currentDay = currentDay.plusWeeks(1);
        }

        // 해당 달의 현재 날짜 이후의 해당 요일에 해당하는 모든 날짜 추가함
        while (!currentDay.isAfter(lastDayOfMonth)) {
            dates.add(currentDay);
            currentDay = currentDay.plusWeeks(1);
        }

        return dates;
    }
}
