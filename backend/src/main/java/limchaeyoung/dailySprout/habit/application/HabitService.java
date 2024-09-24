package limchaeyoung.dailySprout.habit.application;

import jakarta.transaction.Transactional;
import limchaeyoung.dailySprout.achievement.domain.Achievement;
import limchaeyoung.dailySprout.achievement.repository.AchievementRepository;
import limchaeyoung.dailySprout.habit.domain.Habit;
import limchaeyoung.dailySprout.habit.domain.HabitStatus;
import limchaeyoung.dailySprout.habit.dto.CreateHabitRequest;
import limchaeyoung.dailySprout.habit.dto.HabitInfoResponse;
import limchaeyoung.dailySprout.habit.exception.CustomHabitException;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static limchaeyoung.dailySprout.common.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HabitService {
    private final HabitRepository habitRepository;
    private final AchievementRepository achievementRepository;

    private final UserService userService;

    private final HabitMapper habitMapper;

    public List<HabitInfoResponse> findAllByUserAndStatus(String email) {
        User user = userService.findByEmail(email);
        List<Habit> habits = habitRepository.findAllByUserAndStatus(user, HabitStatus.ACTIVE);

        return habits.stream().map(habitMapper::toHabitInfoResponse).toList();
    }

    public void deactivateHabit(Long habitId, String email) {
        User user = userService.findByEmail(email);
        Habit habit = habitRepository.findByHabitIdAndUser(habitId, user).orElseThrow(() -> new CustomHabitException(HABIT_NOT_FOUND));

        habit.deactivate();
    }


    public Habit createHabit(CreateHabitRequest createHabitRequest, String email) {
        User user = userService.findByEmail(email);

        // 습관 저장
        Habit habit = habitMapper.toEntity(createHabitRequest, user);
        habitRepository.save(habit);

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        if (createHabitRequest.dayWeeks().contains(DayWeek.valueOf(dayOfWeek.toString()))) {
            Achievement achievement = Achievement.builder()
                    .habit(habit)
                    .habitDate(LocalDate.now())
                    .build();
            achievementRepository.save(achievement);
        }

        return habit;
    }
}


